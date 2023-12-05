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
            <LogoImg src={CarrotLogo}/>
          </Link>
        </Logo>
        <NavWrap>
          <MenuList>
            <Menu>
              <MenuLink to="/fleamarket">중고거래</MenuLink>
              </Menu>
            <Menu>
              <MenuLink>동네업체</MenuLink>
              </Menu>
            <Menu>
              <MenuLink>알바</MenuLink>
              </Menu>
            <Menu>
              <MenuLink>부동산 직거래</MenuLink>
              </Menu>
            <Menu>
              <MenuLink>중고차 직거래</MenuLink>
              </Menu>
          </MenuList>
        </NavWrap>
        <SearchWrap>
          <span>
            <SearchBtn>
              <img src={SearchIcon} />
            </SearchBtn>
            <SearchForm>
              <SearchInput placeholder="물품이나 동네를 검색해보세요"/> 
            </SearchForm>
          </span>
          <span>
            <MenuBtn></MenuBtn>
            <ChatBtn>채팅하기</ChatBtn>
          </span>
        </SearchWrap>
      </MenuWrap>
    </HeaderWrap>
  );
};

export default Header;
const HeaderWrap = styled.div`
  background-color: white;
  color: black;
  // position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  max-width: 100vw;
  z-index: 999;
`;
const MenuWrap = styled.div`
  max-width: 75rem;
  padding: 12px 20px;
  // height: 40px;
  box-sizing: border-box;
  justify-content: space-between;
  width: 100%;
  margin: 0 auto;
  position: relative;
  display: flex;
  align-items: center;
  margin: 0 auto;
`;
const LogoImg = styled.img`
  width: 65px;
  height: 36px;
  fill: none;
`;
const Logo = styled.a`
  display: inline-flex;
  align-items: center;
  margin-right: 3.6rem;
`;
const NavWrap = styled.nav`
  display: flex;
  align-items: center;
  width: 100%;
  padding-top: 0;
  padding-right: 4rem;
  padding-bottom: 0;
  padding-left: 0;
`;
const MenuList = styled.ul`
  display: inline-block;
  list-style: none;
  padding: 0;
  margin: 0;
`;
const Menu = styled.li`
  display: inline-block;
  margin: 0;
`;
const MenuLink = styled(Link)`
  line-height: 1.32;
  font-size: 1.2rem;
  letter-spacing: -2%;
  color:#4d5159;
  font-weight: 700;
  font-style: normal;
  margin-right: 1.2rem;
  text-decoration: none;
  cursor: pointer;
`;
const SearchWrap = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
const SearchBtn = styled.button`
  display: none;
  margin-right: 1.6rem;
`;
const SearchForm = styled.form`
  display: block;
  position: relative;
  margin-right: 1.2rem;
`;
const SearchInput = styled.input`
  width: 288px;
  height: 40px;
  border: none;
  border-radius: 10px;
  background-color: #f2f3f6;
  color : B1B4BB;
  font-weight: bold;
  padding-left: 1rem;
  @media screen and (max-width: 1000px) {
  display: none;
`;

const MenuBtn = styled.button`
  display: none;
`;
const ChatBtn = styled.button`
  width: 100px;
  background-color: white;
  border: 1px solid #d7d8dd;
  height: 40px;
  // margin: 0 1rem;
  border-radius: 5px;
  font-weight: bold;
  font-size: 16px;
`;