import React from "react";
import styled from "styled-components";

const StoreBtn = () => {
  return (
    <div>
      <StoreButton>Google Play</StoreButton>
      <StoreButton>App Store</StoreButton>
    </div>
  );
};

export default StoreBtn;

const StoreButton = styled.button`
  width: 10rem;
  height: 3rem;
  background-color: #ff6f0f;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: bold;
  color: white;
  margin-right: 1rem;
`;
