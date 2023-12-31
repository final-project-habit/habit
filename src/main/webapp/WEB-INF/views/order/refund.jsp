<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="/css/refund.css?after" />'
<script src="/js/refund.js"></script>
    <!-- 본문 시작 -->
    <form action="/mypage/refund" method="POST" onsubmit="return refundCheck()">
    <div class="Home">
        <div class="Home_order_sub">환불 정보</div>
        <div class="Home_show_wrapper">
            <img src="/storage/${refundInfo.cont_img}" alt="" class="Home_show_order_img">
            
                <div class="Home_show_order_info">
                    <div class="Home_show_date">${refundInfo.payd_date} 결제</div> |
                    <div class="Home_show_num"> 구매수량 : <span><input type="text" class="refn_qty" value="${refundInfo.payd_qty}" name="refn_qty" readonly></span></div>
                    <div>${refundInfo.cont_name}</div>
                    <div>${refundInfo.op_name}</div>
                </div>
            
        </div>

        <hr>
        <div class="Home_order_sub2">환불 정보</div>
        <div class="Home_order_check">
            <div>
                <div class="Home_order_check_sub">
                    상품 금액
                </div>
                <div class="Home_order_check_num">
                    <c:set value="${refundInfo.payd_qty*refundInfo.payd_price}" var="price"/>
                    <fmt:formatNumber value="${price}" var="total" pattern="#,###"/>
                    ${total}
                    <span>원</span>
                </div>
            </div>
            <div>
                <div class="Home_order_check_sub">
                    할인 금액
                </div>
                <div class="Home_order_check_num">
                    <span></span>
                    <span>-</span>0<span>원</span>
                </div>
            </div>
            <div>
                <div class="Home_order_check_sub">
                    환불 금액
                </div>
                <div class="Home_order_check_num">
                    <c:set value="${refundInfo.payd_qty*refundInfo.payd_price-refundInfo.pay_point}" var="reprice"/>
                    <fmt:formatNumber value="${reprice}" var="retotal" pattern="#,###"/>
                    <input type="text" name="refn_price" class="refn_price" value="${retotal}"><span>원</span>
                </div>
            </div>

            <div>
                <div class="Home_order_check_sub">
                    환불 에너지
                </div>
                <div class="Home_order_check_num">
                    <span></span>
                    <input type="text" name="refn_point" class="refn_point" value="-${refundInfo.pay_point}"><span>원</span>
                </div>
            </div>
            <div>
                <div class="Home_order_check_sub">
                    환불 수단
                </div>
                <div class="Home_order_check_num">
                  <c:if test="${refundInfo.pay_method=='C'}">
                      카드
                  </c:if>
                    <input type="hidden" value="${refundInfo.pay_method}" name="pay_method">
                </div>
            </div>
        </div>
        <hr>
        <div class="Home_refund_explain">
            <div style="font-size: 14px; font-weight: 600;">환불정책</div><br>
            <div style="font-size: 12px; font-weight: 600; margin-bottom: 5px;">[날짜 조율형]</div>
            <div style="margin-bottom: 3px;">1. 결제 후 7일 이내 취소 시 : 전액 환불<br>
                (단, 결제 후 14일 이내라도 호스트와 해빗 진행일 예약 확정 후 환불 불가)</div>
            <div style="margin-bottom: 3px;">2. 결제 후 14일 이후 취소 시 : 환불 불가</div>
            ※ 상품의 유효기간 만료 시 연장은 불가합니다.<br>
            ※ 다회권의 경우, 1회라도 사용시 부분 환불이 불가합니다.<br>
            <br>
            <div style="font-size: 12px; font-weight: 600;margin-bottom: 5px;">[날짜 지정형]</div>
            <div style="margin-bottom: 3px;">1. 구매한 클래스 이용권 사용일 전 취소 시 : 전액 환불</div>
            <div style="margin-bottom: 3px;">2. 구매한 클래스 이용권 사용일 이후 취소 시 : 환불 불가</div>
            <div style="margin-bottom: 3px;">※ 상품의 유효기간 만료 시 연장은 불가합니다.</div>
        </div>
        <hr>
        <div class="Home_refund_btn">
          <div><button class="refund_btn" type="submit">환불하기</button></div>
        </div>
    </div>
    <input type="hidden" name="payd_no" value="${refundInfo.payd_no}">
    <input type="hidden" name="pro_no" value="${refundInfo.pro_no}">
    </form>
    <!-- 본문 끝-->
    <%@include file="../footer.jsp"%>