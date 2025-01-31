import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import UserMainComponent from './Components/UserMainComponent';
import UserLoginComponent from './Components/UserLoginComponent';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<UserMainComponent />} />
          <Route path="/login" element={<UserLoginComponent />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
