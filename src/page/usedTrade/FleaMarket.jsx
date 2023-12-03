import React from "react";
import styled from "styled-components";
import TopImg from "../../assets/img/fleamarketTopImg.webp";

const FleaMarket = () => {
  return (
    <>
      <MainTop>
        <TitleWrap>
          <TitleText>
            믿을만한
            <br />
            이웃 간 중고거래
          </TitleText>
          <p>동네 주민들과 가깝고 따뜻한 거래를</p>
          <p>지금 경험해보세요.</p>
        </TitleWrap>
        <ImgDiv>
          <ImgChar src={TopImg} />
        </ImgDiv>
      </MainTop>
    </>
  );
};

export default FleaMarket;

const MainTop = styled.div`
  background-color: #fffae0;
  height: 719px;
  display: flex;
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
  border: 1px solid red;
  width: 50%;
  height: 100%;
`;

const ImgChar = styled.img`
  width: 100%;
  height: 100%;
`;
