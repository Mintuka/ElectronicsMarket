/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication;

class DataModel {

    private String title;
    private String price;
    private String info;
    private String buy;
    private int imageResource;


    DataModel(String title,String price, String info, String buy,int imageResource) {
        this.title = title;
        this.price = price;
        this.info = info;
        this.buy = buy;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {return price;}

    public String getInfo() {
        return info;
    }

    public String getBuy(){return buy;}

    public int getImageResource() {

        return imageResource;
    }
}