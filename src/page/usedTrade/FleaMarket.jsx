import React from "react";
import styled from "styled-components";
import TopImg from "../../assets/img/fleamarketTopImg.webp";

const FleaMarket = () => {
  return (
    <>
      <FleamarketCover>
        <CoverContent>
          <CoverTitle>
            믿을만한
            <br />
            이웃 간 중고거래
          </CoverTitle>
          <CoverDescription>
            동네 주민들과 가깝고 따뜻한 거래를
            <br/>
            지금 경험해보세요.
          </CoverDescription>
          <CoverImg>
            <FleamarketCoverImg>
              <Img src={TopImg} />
            </FleamarketCoverImg>
          </CoverImg>
        </CoverContent>
      </FleamarketCover>

      <FleamarketArticleList>
        <ArticleListTitle>중고거래 인기매물</ArticleListTitle>
        <CardsWrap>
          <CardTop>
            <CardLink>
            <CardPhoto>
              <CardImg src={TopImg}/>
            </CardPhoto> 
            <CardDesc>
              <CardTitle>에어팟 프로2 새상품</CardTitle>
              <CardPrice>70,000원</CardPrice>
              <CardRegionName>서울 송파구 가락2동</CardRegionName>
              <CardCounts>
                <span>관심 21</span>
                ∙
                <span>채팅 49</span>
              </CardCounts>
            </CardDesc>
            </CardLink>
          </CardTop> 
          <CardTop>
            <CardLink>
            <CardPhoto>
              <CardImg src={TopImg}/>
            </CardPhoto> 
            <CardDesc>
              <CardTitle>에어팟 프로2 새상품</CardTitle>
              <CardPrice>70,000원</CardPrice>
              <CardRegionName>서울 송파구 가락2동</CardRegionName>
              <CardCounts>
                <span>관심 21</span>
                ∙
                <span>채팅 49</span>
              </CardCounts>
            </CardDesc>
            </CardLink>
          </CardTop> 
          <CardTop>
            <CardLink>
            <CardPhoto>
              <CardImg src={TopImg}/>
            </CardPhoto> 
            <CardDesc>
              <CardTitle>에어팟 프로2 새상품</CardTitle>
              <CardPrice>70,000원</CardPrice>
              <CardRegionName>서울 송파구 가락2동</CardRegionName>
              <CardCounts>
                <span>관심 21</span>
                ∙
                <span>채팅 49</span>
              </CardCounts>
            </CardDesc>
            </CardLink>
          </CardTop> 
          <CardTop>
            <CardLink>
            <CardPhoto>
              <CardImg src={TopImg}/>
            </CardPhoto> 
            <CardDesc>
              <CardTitle>에어팟 프로2 새상품</CardTitle>
              <CardPrice>70,000원</CardPrice>
              <CardRegionName>서울 송파구 가락2동</CardRegionName>
              <CardCounts>
                <span>관심 21</span>
                ∙
                <span>채팅 49</span>
              </CardCounts>
            </CardDesc>
            </CardLink>
          </CardTop> 
          <CardTop>
            <CardLink>
            <CardPhoto>
              <CardImg src={TopImg}/>
            </CardPhoto> 
            <CardDesc>
              <CardTitle>에어팟 프로2 새상품</CardTitle>
              <CardPrice>70,000원</CardPrice>
              <CardRegionName>서울 송파구 가락2동</CardRegionName>
              <CardCounts>
                <span>관심 21</span>
                ∙
                <span>채팅 49</span>
              </CardCounts>
            </CardDesc>
            </CardLink>
          </CardTop> 
          <CardTop>
            <CardLink>
            <CardPhoto>
              <CardImg src={TopImg}/>
            </CardPhoto> 
            <CardDesc>
              <CardTitle>에어팟 프로2 새상품</CardTitle>
              <CardPrice>70,000원</CardPrice>
              <CardRegionName>서울 송파구 가락2동</CardRegionName>
              <CardCounts>
                <span>관심 21</span>
                ∙
                <span>채팅 49</span>
              </CardCounts>
            </CardDesc>
            </CardLink>
          </CardTop> 
          <CardTop>
            <CardLink>
            <CardPhoto>
              <CardImg src={TopImg}/>
            </CardPhoto> 
            <CardDesc>
              <CardTitle>에어팟 프로2 새상품</CardTitle>
              <CardPrice>70,000원</CardPrice>
              <CardRegionName>서울 송파구 가락2동</CardRegionName>
              <CardCounts>
                <span>관심 21</span>
                ∙
                <span>채팅 49</span>
              </CardCounts>
            </CardDesc>
            </CardLink>
          </CardTop> 
          <CardTop>
            <CardLink>
            <CardPhoto>
              <CardImg src={TopImg}/>
            </CardPhoto> 
            <CardDesc>
              <CardTitle>에어팟 프로2 새상품</CardTitle>
              <CardPrice>70,000원</CardPrice>
              <CardRegionName>서울 송파구 가락2동</CardRegionName>
              <CardCounts>
                <span>관심 21</span>
                ∙
                <span>채팅 49</span>
              </CardCounts>
            </CardDesc>
            </CardLink>
          </CardTop> 
          <CardTop>
            <CardLink>
            <CardPhoto>
              <CardImg src={TopImg}/>
            </CardPhoto> 
            <CardDesc>
              <CardTitle>에어팟 프로2 새상품</CardTitle>
              <CardPrice>70,000원</CardPrice>
              <CardRegionName>서울 송파구 가락2동</CardRegionName>
              <CardCounts>
                <span>관심 21</span>
                ∙
                <span>채팅 49</span>
              </CardCounts>
            </CardDesc>
            </CardLink>
          </CardTop> 
        </CardsWrap>
      </FleamarketArticleList>
    </>
  );
};

export default FleaMarket;

const FleamarketCover = styled.section`
  background-color: #FFF1AA;
  height: 315px;
  // display: flex;
  display: block;
  padding: 0 16px 0 16px;
`;

const CoverContent = styled.div`
  position: relative;
  height: 100%;
  box-sizing: border-box;
  max-width: 768px;
  margin: 0 auto;
  padding-top: 50px;
`;

const CoverTitle = styled.h1`
  line-height: 1.5;
  font-size: 34px;
  font-weight: bold;
  letter-spacing: -0.32px;
  margin-bottom: 1rem;
`;
const CoverDescription = styled.span`
  line-height: 1.32;
  font-size: 18px;
  letter-spacing: -0.18px;
  margin-top: 16px;
  display: block;
`;

const CoverImg = styled.div`
  position: absolute;
  bottom: 0;
  display: flex;
  right: 0;
  height: 315px;
  width: 416px;
`;
const FleamarketCoverImg = styled.span`
box-sizing: border-box;
overflow: hidden;
width: initial;
height: initial;
background: none;
opacity: 1;
border: 0;
margin: 0;
padding: 0;
position: absolute;
top: 0;
left: 0;
bottom: 0;
right: 0;
display:block;
`;

const Img = styled.img`
position: absolute;
top: 0;
left: 0;
bottom: 0;
right: 0;
box-sizing: border-box;
padding: 0;
border: none;
margin: auto;
width: 0;
height: 0;
min-width: 100%;
max-width: 100%;
min-height: 100%;
max-height: 100%;
object-fit: cover;
`;

const FleamarketArticleList = styled.section`
  padding-top: 64px;
  display: block;
`;
const ArticleListTitle = styled.h1`
font-size: 32px;
line-height: 43.2px;
text-align: center;
`;
const CardsWrap = styled.div`
margin-top: 40px;
width: 100%;
display: flex;
justify-content: space-between;
flex-wrap: wrap;
margin: 0 auto;
`;
const CardTop = styled.article`
width: 223px;
margin-bottom: 56px;
display: block;
`;
const CardLink = styled.a`
text-decoration: none;
color: #212529;
`;
const CardPhoto = styled.div`
width: 100%;
padding-top: 100%;
position: relative;
overflow: hidden;
border-radius: 12px;
background-color: #F8F9FA;
box-shadow: inset 0px 0px 0px 1px rgba(0, 0, 0, 0.15);
box-sizing: border-box;
`;
const CardImg = styled.img`
position: absolute;
top: 0;
bottom: 0;
width: 100%;
box-sizing: border-box;
border-radius: 12px;
border: 1px solid transparent;
`;
const CardDesc = styled.div`
margin-top: 12px;
`;
const CardTitle = styled.h2`
font-size: 16px;
letter-spacing: -0.02px;
color: #212529;
overflow: hidden;
white-space: nowrap;
text-overflow: ellipsis;
margin-bottom: 4px;
line-height: 1.5;
font-weight: normal;
`;
const CardPrice = styled.div`
font-size: 15px;
font-weight: 700;
line-height: 1.5;
margin-bottom: 4px; 
`;
const CardRegionName = styled.div`
font-size: 13px;
color: #212529;
overflow: hidden;
white-space: nowrap;
text-overflow: ellipsis;
margin-bottom: 4px;
line-height: 1.5;
`;
const CardCounts = styled.div`
color: #868e96;
font-size: 13px;
`;

