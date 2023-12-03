import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Main from "./page/MainPage";
import Footer from "./page/Footer";
import Login from "./page/Login";
import FleaMarket from "./page/usedTrade/FleaMarket"
import Header from "./page/Header";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" element={<Main />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/fleamarket" element={<FleaMarket />}></Route>
        </Routes>
      </BrowserRouter>
      <Footer />
    </div>
  );
}

export default App;
