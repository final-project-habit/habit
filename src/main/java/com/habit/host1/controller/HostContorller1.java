package com.habit.host1.controller;

import com.habit.host1.DTO.RequestContentInsertDTO;
import com.habit.host1.DTO.RequestReviewDTO;
import com.habit.host1.DTO.ResponseReviewDTO;
import com.habit.host1.DTO.*;
import com.habit.host1.service.HostService1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/host")
public class HostContorller1 {

    private final HostService1 hostService1;

    // [habit_create.jsp]
    // 컨텐츠 생성 폼으로 이동 & 대분류 list 불러오기
    @GetMapping("/content/form")
    public String contentForm(@SessionAttribute(name = "userId", required = false) String userIdd, Model model) {
        String userId = "user-1"; //임시 세션 아이디
        model.addAttribute("List", hostService1.cateList());
        return "host/habit_create";
    }

    // 대분류 선택에 따른 중분류 list 불러오기
    @GetMapping("/cate_middle.do")
    @ResponseBody
    public List<Map<String, Object>> selectCate(HttpServletRequest request) {
        String cate_large = request.getParameter("cate_large");
        return hostService1.selectCate(cate_large);
    }

    // 생성된 컨텐츠 값 insert
    @PostMapping("/content/insert")
    public String contentInsert(@SessionAttribute(name = "userId", required = false) String userIdd, RequestContentInsertDTO rciDTO) throws IOException {
        //임시 세션 아이디
        String userId = "user-1";
        rciDTO.setHost_id(userId);
        int result = hostService1.contentInsert(rciDTO);
        return "redirect:/host/content/list";
    }

    @GetMapping("/content/list")
    public String contentList (@SessionAttribute(name = "userId", required = false) String userIdd, Model model) {
        //임시 세션 아이디
        String userId = "user-1";
        RequestContentListDTO reqContListDTO = new RequestContentListDTO();
        reqContListDTO.setHost_id(userId);

        List<ResponseContentListDTO> list = hostService1.contentList(reqContListDTO);
        model.addAttribute("list", list);

        return "host/habit_list";
    }

    @PostMapping("/content/list.do")
    @ResponseBody
    public List<ResponseContentListDTO> contentList (@SessionAttribute(name = "userId", required = false) String userIdd, RequestContentListDTO reqContListDTO) {
        //임시 세션 아이디
        String userId = "user-1";
        reqContListDTO.setHost_id(userId);
        return hostService1.contentList(reqContListDTO);
    }

    @GetMapping("/content/delete/{cont_no}")
    public String contentDelete(@PathVariable int cont_no) {
        hostService1.deleteContent(cont_no);
        return "redirect:/host/content/list";
    }

    @GetMapping("/content/update/{cont_no}")
    @ResponseBody
    public RequestContentInsertDTO contentUpdateBefore(@PathVariable int cont_no, Model model) {
        return hostService1.contentSelectOne(cont_no);
    }


    // [habit_review_control.jsp]
    @GetMapping("/review")
    public String reviewSearch(@SessionAttribute(name = "userId", required = false) String userIdd, Model model) {
        //임시 세션 아이디
        String userId = "user-1";
        RequestReviewDTO reqReviewDTO = new RequestReviewDTO();
        reqReviewDTO.setHost_id(userId);

        List<ResponseReviewDTO> list = hostService1.reviewList(reqReviewDTO);
        int totalCount = hostService1.totalReviewCount(reqReviewDTO);

        ResponsePageVO responsePageVO = new ResponsePageVO();
        responsePageVO.setTotalRecord(totalCount);

        model.addAttribute("list", list);
        model.addAttribute("paging", responsePageVO);

        return "host/habit_review_control";
    }

    @GetMapping("/review/{currentPage}")
    public String reviewSearch(@SessionAttribute(name = "userId", required = false) String userIdd, @PathVariable Integer currentPage, Model model) {
        //임시 세션 아이디
        String userId = "user-1";
        RequestReviewDTO reqReviewDTO = new RequestReviewDTO();
        reqReviewDTO.setHost_id(userId);
        ResponsePageVO responsePageVO = new ResponsePageVO(currentPage);
        reqReviewDTO.setVo(responsePageVO);

        List<ResponseReviewDTO> list = hostService1.reviewList(reqReviewDTO);
        int totalCount = hostService1.totalReviewCount(reqReviewDTO);
        responsePageVO.setTotalRecord(totalCount);

        model.addAttribute("list", list);
        model.addAttribute("paging", responsePageVO);

        return "host/habit_review_control";
    }


    @PostMapping("/review.do")
    @ResponseBody
    public List<ResponseReviewDTO> reviewSearch(@SessionAttribute(name = "userId", required = false) String userIdd, RequestReviewDTO reqReviewDTO) {
        //임시 세션 아이디
        String user_id = "user-1";
        reqReviewDTO.setHost_id(user_id);
        System.out.println("reqReviewDTO = " + reqReviewDTO);
        System.out.println(hostService1.reviewList(reqReviewDTO));
        return hostService1.reviewList(reqReviewDTO);
    }
//
//    // 리뷰 페이징 번호에 따른 list 불러오기
//    @GetMapping("/review/{page}")
//    @ResponseBody
//    public List<ResponseReviewDTO> reviewPaging(@SessionAttribute(name = "userId", required = false) String userIdd, @PathVariable int page) {
//        //임시 세션 아이디
//        String user_id = "user-1";
//        return null;
//    }



    // [habit_reservation_control.jsp]
    @GetMapping("/reservation")
    public String reservation (@SessionAttribute(name = "userId", required = false) String userIdd, Model model) {
        //임시 세션 아이디
        String userId = "user-1";
        RequestReservationDTO reqReservDTO = new RequestReservationDTO();
        reqReservDTO.setHost_id(userId);
        List<ResponseReservationDTO> list = hostService1.reservationList(reqReservDTO);
        model.addAttribute("list", list);
        return "host/habit_reservation_control";
    }

    // 예약 상태 변경
    @PostMapping("/reservation/statuschange.do")
    @ResponseBody
    public int reservationStatusChange (RequestReservationStatusChangeDTO reqReservStatChg) {
        return hostService1.reservationStatusChangeAndRefundInsert(reqReservStatChg);
    }

    // 예약 리스트 필터 조회
    @PostMapping("/reservation/filter.do")
    @ResponseBody
    public List<ResponseReservationDTO> reservationFilter (@SessionAttribute(name = "userId", required = false) String userIdd, RequestReservationDTO reqReservDTO) {
        String userId = "user-1";
        reqReservDTO.setHost_id(userId);
        System.out.println(reqReservDTO.getSearchStartDate());
        System.out.println(reqReservDTO.getSearchEndDate());
        return hostService1.reservationList(reqReservDTO);
    }


    // [habit_inquiry_control.jsp]
    @GetMapping("/inquiry")
    public String inquiryControl() {
        return "host/habit_inquiry_control";
    }

    @PostMapping("/inquiry.do")
    @ResponseBody
    public List<ResponseInquiryDTO> inquiryList(@SessionAttribute(name = "userId", required = false) String userIdd, RequestInquiryDTO reqInqDTO) {
        //임시 세션 아이디
        String userId = "user-1";
        reqInqDTO.setHost_id(userId);
        return hostService1.inquiryList(reqInqDTO);
    }

}
