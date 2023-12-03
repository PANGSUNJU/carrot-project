import React from "react";
import styled from "styled-components";

import StoreBtn from "../component/StoreBtn";
import Character from "../assets/img/mainCharacter.webp";

const MainPage = () => {
  return (
    <>
      <MainTop>
        <TitleWrap>
          <TitleText>
            당신 근처의
            <br />
            지역 생활 커뮤니티
          </TitleText>
          <p>동네라서 가능한 모든 것</p>
          <p>당근에서 가까운 이웃과 함께해요.</p>
          <StoreBtn />
        </TitleWrap>
        <ImgDiv>
          <ImgChar src={Character} />
        </ImgDiv>
      </MainTop>
    </>
  );
};

export default MainPage;

const MainTop = styled.div`
  background-color: #fffae0;
  height: 719px;
  display: flex;
  width: 100%;
`;

const TitleWrap = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 50%;
  margin-left: 4rem;
`;

const TitleText = styled.p`
  font-size: 4rem;
  font-weight: bold;
  margin-bottom: 1rem;
`;

const ImgDiv = styled.div`
  // border: 1px solid red;
  width: 50%;
  height: 100%;
`;

const ImgChar = styled.img`
  width: 100%;
  height: 100%;
`;
