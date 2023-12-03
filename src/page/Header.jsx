import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

import CarrotLogo from "../assets/Logo.svg";
import SearchIcon from "../assets/SearchIcon.svg";

const Header = () => {
  return (
    <HeaderWrap>
      <MenuWrap>
        <Logo>
          <Link to="/">
            <img src={CarrotLogo} />
          </Link>
        </Logo>
        <ManuList>
          <NavMenu to="/fleamarket">중고거래</NavMenu>
          <NavMenu>동네업체</NavMenu>
          <NavMenu>알바</NavMenu>
          <NavMenu>부동산 직거래</NavMenu>
          <NavMenu>중고차 직거래</NavMenu>
        </ManuList>
      </MenuWrap>
      <SearchWrap>
        <SearchInput type="text" placeholder="물품이나 동네를 검색해보세요" />
        <SearchBtn>
          <img src={SearchIcon} />
        </SearchBtn>
        <ChatBtn> 채팅하기</ChatBtn>
      </SearchWrap>
    </HeaderWrap>
  );
};

export default Header;

const HeaderWrap = styled.div`
  width: 100%;
  display: flex;
  // background-color: coral;
  // border: 1px solid red;
  // color: white;
  justify-content: space-between;
  align-content: center;
  @media screen and (max-width: 1100px) {
    justify-content: flex-start;
  }
`;
const MenuWrap = styled.div`
  display: flex;
`;
const Logo = styled.div`
  display: flex;
  align-items: center;
  margin-left: 1rem;
`;
const ManuList = styled.div`
  display: flex;
  // grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  align-item: center;
  margin: 0.5rem 1rem;

  @media screen and (max-width: 1100px) {
    grid-template-columns: repeat(3, minmax(0.5fr, 1fr));
    grid-template-rows: 1fr 1fr;
  }
`;

const NavMenu = styled(Link)`
  font-size: 18px;
  margin: 0.5rem 1rem;
  text-decoration-line: none;
  font-weight: bold;
  color: #818389;
`;
const SearchWrap = styled.div`
  display: flex;
  align-items: center;
`;
const SearchInput = styled.input`
  width: 288px;
  height: 40px;
  border: none;
  border-radius: 10px;
  background-color: #f2f3f6;
  font-weight: bold;
  padding-left: 1rem;
  @media screen and (max-width: 1000px) {
    display: none;
  }
`;
const SearchBtn = styled.div`
  display: none;
  @media screen and (max-width: 1000px) {
    width: 2rem;
    height: 2rem;
    // border: 1px solid red;
    display: block;
  }
`;
const ChatBtn = styled.button`
  width: 100px;
  background-color: white;
  border: 1px solid #d7d8dd;
  height: 40px;
  margin: 0 1rem;
  border-radius: 5px;
  font-weight: bold;
  font-size: 16px;
`;
