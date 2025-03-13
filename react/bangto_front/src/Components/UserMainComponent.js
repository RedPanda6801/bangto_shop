import React, { useState } from "react";
import './UserMainComponent.css';
import UserMainSearchComponent from "./UserMainSearchComponent";
import UserMainRecommendComponent from "./UserMainRecommendComponent";
import UserCartListComponent from "./UserCartListComponent"

const UserMainComponent = () => 
{    
    const [selectedCategory, setSelectedCategory] = useState("");

    return (
        <div className="layout_Main_Contents">
            <UserMainSearchComponent />
            <UserMainRecommendComponent />
            <UserCartListComponent />
        </div>
    );
}

export default UserMainComponent;