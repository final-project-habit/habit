package com.habit.cart;

import com.habit.energy.EnergyDAO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@Slf4j
public class CartCont {

    public CartCont() {
        System.out.println("----CartCont() 객체 생성됨");
    }

    @Autowired
    CartDAO cartDAO;

    @Autowired
    EnergyDAO energyDAO;

    @RequestMapping(value="/cart/insert", method = RequestMethod.POST, consumes = "application/json")
    //@ResponseBody
    @Transactional
    public String cartInsert(@SessionAttribute(name = "s_id",required = false)String user_id,@RequestBody List<CartInsertDTO> sendparams , HttpSession session){
        log.info("params={}",sendparams);
        for (CartInsertDTO sendparam : sendparams) {
            sendparam.setUser_id(user_id);
            Map<String, Object> checkCart = cartDAO.checkCart(sendparam);
            if(Integer.parseInt(String.valueOf(checkCart.get("count")))!=0L){
                int allqty = sendparam.getCl_qty() + Integer.parseInt(String.valueOf(checkCart.get("cl_qty")));
                sendparam.setCl_qty(allqty);
                cartDAO.updateCart(sendparam);
            }else{
                cartDAO.cartInsert(sendparam);
            }

        }

        return "redirect:/cart/list";
    }

    @RequestMapping(value="/cart/list",method = RequestMethod.GET)
    public ModelAndView list(@SessionAttribute(name = "s_id",required = false)String user_id, HttpSession session, HttpServletRequest req){
        //String user_id="user-3";

        //System.out.println(cartDAO.oneday_list("user-3"));
        List<CartDTO> list1 = cartDAO.oneday_list(user_id);
        List<CartDTO> list2 = cartDAO.prod_list(user_id);
        System.out.println("list2 = " + list2);
        System.out.println("list1 = " + list1);
        List<CartDTO> list = new ArrayList<>();
        list.addAll(list1);
        list.addAll(list2);

        //log.info("CartDTOList={}",list);


        ModelAndView mav=new ModelAndView();
        mav.setViewName("order/cart");
        //mav.addObject("list1", cartDAO.oneday_list(user_id));
        //mav.addObject("list2", cartDAO.prod_list(user_id));
        mav.addObject("list", list);
        //mav.addObject("totPrice", totPrice);
        return mav;

    }

    @RequestMapping(value="/cart/order/payPage", method=RequestMethod.GET)
    public ModelAndView orderSelectedItems(@SessionAttribute(name = "s_id",required = false)String user_id, @RequestParam(value = "cartno")String ch, HttpServletRequest req){
        //System.out.println(ch);

        List<Integer> carts=new ArrayList<>();
        String[] cartnos= ch.split("-");// split으로 자르고 자른 조각들을 배열에 담는다.
        for (String cartno : cartnos) { // 배열에 담긴 문자열들을 하나씩 꺼내서 정수로 변환 후 리스트에 담기
            //System.out.println("cartno = " + cartno);
            carts.add(Integer.valueOf(cartno));
        }
        req.setAttribute("carts", carts);
        //System.out.println(carts);

        //String user_id="user-3";
        HashMap<String, Object> map=new HashMap<>();
        map.put("user_id", user_id);
        map.put("carts", carts);

        List<CartDTO> cartDTOS=cartDAO.selectedItemsInfo(map);
        System.out.println(cartDTOS);
        //log.info("carts={}",carts);

        for(CartDTO dto:cartDTOS){
            String cont_img=dto.getCont_img().trim().split("\\|")[0];
            dto.setCont_img(cont_img);
        }

        ModelAndView mav=new ModelAndView();


        mav.setViewName("order/payPage");
        mav.addObject("cartDTOS", cartDTOS);
        mav.addObject("num", cartDTOS.size());
        mav.addObject("energy",energyDAO.getSavedEnergy(user_id));



        return mav;

    }

    //해빈함

    //카트 삭제
    @PostMapping(value="/cart/delete")
    @ResponseBody
    public String delete(@SessionAttribute(name = "s_id",required = false) String user_id
                        ,@RequestParam( "cl_nos") String[] params, HttpServletRequest req){

        List<String> list = Arrays.stream(params).toList();
        log.info("list={}",list);
        HashMap<String, Object> map=new HashMap<>();
        map.put("cl_nos", list);
        map.put("user_id", user_id);
        int i = cartDAO.cartDelete(map);
        if(i==0){
            return "NOK";
        }

        return "OK";
    }

    //카트수량 변경
    @PostMapping("/cart/change")
    @ResponseBody
    public String changeQty(@SessionAttribute(name = "s_id",required = false) String user_id
                            ,@RequestParam("cl_nos")String cl_nos,@RequestParam("qty")String qty){

        log.info("cl_nos={}",cl_nos);
        log.info("qty={}",qty);


        String[] cartnos= cl_nos.split("-");// split으로 자르고 자른 조각들을 배열에 담는다.
        String[] qtys=qty.split("-");
        log.info("cl_nos={}",cartnos);
        log.info("qty={}",qtys);
        int status=0;
       for(int i=0;i<cartnos.length;i++){
           String cl_no=cartnos[i];
           int cl_qty= Integer.parseInt(qtys[i]);

           Map<String,Object> params= new HashMap<>();
           params.put("cl_no",cl_no);
           params.put("cl_qty",cl_qty);
           params.put("user_id",user_id);

           status += cartDAO.cartChage(params);

       }

       if(status!=cartnos.length){
           return "NOK";
       }

        return "OK";
    }




}
