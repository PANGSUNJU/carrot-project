import React from "react";
import styled from "styled-components";
import AppStore from "../assets/AppStore.svg";

const Footer = () => {
  return (
    <FooterWrap>
      <FooterMenu>
        <FooterMenuList>
          <MenuUl>
            <MenuLi>중고거래</MenuLi>
            <MenuLi>동네업체</MenuLi>
            <MenuLi>당근알바</MenuLi>
            <MenuLi>부동산 직거래</MenuLi>
            <MenuLi>중고차 직거래</MenuLi>
          </MenuUl>
          <MenuUl>
            <MenuLi>당근비즈니스</MenuLi>
            <MenuLi>채팅하기</MenuLi>
          </MenuUl>
          <MenuUl>
            <MenuLi>자주 묻는 질문</MenuLi>
            <MenuLi>회사 소개</MenuLi>
            <MenuLi>인재 채용</MenuLi>
          </MenuUl>
        </FooterMenuList>
        <div>
          <p>당근 앱 다운로드</p>
          <div>
            <button>App Store</button>
            <button>Google Play</button>
          </div>
        </div>
      </FooterMenu>
    </FooterWrap>
  );
};

export default Footer;

const FooterWrap = styled.div`
  width: 100%;
  height: 390px;
  border: 1px solid red;
`;

const FooterMenu = styled.div`
  display: flex;
  padding: 4rem;
  justify-content: center;

  @media screen and (max-width: 1000px) {
    width: 1000px;
    justify-content: flex-start;
  }
`;

const FooterMenuList = styled.div`
  display: flex;
`;
const MenuUl = styled.ul`
  margin-right: 2rem;
`;
const MenuLi = styled.li`
  list-style-type: none;
  margin-bottom: 1.5rem;
`;
