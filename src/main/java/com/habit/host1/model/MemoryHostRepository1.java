package com.habit.host1.model;

import com.habit.host1.DTO.*;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MemoryHostRepository1 implements HostRepository1{

    private final SqlSession sqlSession;

    // 카테고리 대분류 list 불러오기
    @Override
    public List<Map<String, Object>> cateList() {
        return sqlSession.selectList("host1.cateListLage");
    }

    // 대분류 선택에 따른 중분류 list 불러오기
    @Override
    public List<Map<String, Object>> selectCate(String cateLarge) {
        return sqlSession.selectList("host1.cateListMiddle", cateLarge);
    }

    // 카테고리코드 가져오기
    @Override
    public String selectCateNo(String cate_middle) {
        return sqlSession.selectOne("host1.selectCateNo", cate_middle);
    }

    // 콘텐츠 테이블 insert
    @Override
    public int insertCont(RequestContentInsertDTO rciDTO) {
        return sqlSession.insert("host1.insertCont", rciDTO);
    }

    // 원데이 클래스 테이블 insert
    @Override
    public int insertOne(List<OneEntity> list) {
        return sqlSession.insert("host1.insertOne", list);
    }

    // 인원권, 회차권 테이블 insert
    @Override
    public int insertProd(List<ProdEntity> list) {
        return sqlSession.insert("host1.insertProd", list);
    }

    // 리뷰 테이블 불러오기
    @Override
    public List<ResponseReviewDTO> reviewList(RequestReviewDTO reqReviewDTO) {
        return sqlSession.selectList("host1.reviewList", reqReviewDTO);
    }

    // 리뷰 검색 count 수 가져오기
    @Override
    public int totalCount(RequestReviewDTO reqReviewDTO) {
        return sqlSession.selectOne("host1.reviewCount", reqReviewDTO);
    }

    // 문의사항 검색 List
    @Override
    public List<ResponseInquiryDTO> inquiryList(RequestInquiryDTO reqInqDTO) {
        return sqlSession.selectList("host1.inquiryList", reqInqDTO);
    }

    // 문의사항 검색 결과 count
    @Override
    public int inquiryCount(RequestInquiryDTO reqInqDTO) {
        return sqlSession.selectOne("host1.inquiryCount", reqInqDTO);
    }

    // habit 목록 검색 List
    @Override
    public List<ResponseContentListDTO> contentList(RequestContentListDTO reqContListDTO) {
        return sqlSession.selectList("host1.contentList", reqContListDTO);
    }

    // habit 목록 검색 List count
    @Override
    public int contentListCount(RequestContentListDTO reqContListDTO) {
        return sqlSession.selectOne("host1.contentListCount", reqContListDTO);
    }

    // 원데이 클래스 예약 List 조회
    public List<ResponseReservationDTO> reservationList(String user_id) {
        return sqlSession.selectList("host1.reservationList");
    }
}