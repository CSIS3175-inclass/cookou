package com.example.cookou.models;

import java.util.List;

public class AreaModel {
    private List<AreaModel.Area> meals;

    public List<AreaModel.Area> getMeals() {
        return meals;
    }

    public void setMeals(List<AreaModel.Area> meals) {
        this.meals = meals;
    }

    public static class Area {
        private String strArea;

        public String getStrArea() {
            return strArea;
        }

        public void setStrArea(String strCategory) {
            this.strArea = strCategory;
        }
    }
}
