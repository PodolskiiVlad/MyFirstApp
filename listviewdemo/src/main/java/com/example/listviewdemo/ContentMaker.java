package com.example.listviewdemo;

class ContentMaker {

    private static int labelNumber = 0;

    static String generateImagePath(){
        String result;
        int imgNum = (int) (Math.random() * 6);

        switch (imgNum){
            case 1:
                result = "https://ichef.bbci.co.uk/news/660/cpsprodpb/37B5/production/_89716241_thinkstockphotos-523060154.jpg";
                break;
            case 2:
                result = "https://phillipbrande.files.wordpress.com/2013/10/random-pic-14.jpg";
                break;
            case 3:
                result = "https://habrastorage.org/storage2/927/06e/464/92706e46478df4040ec6e77061d0e2ae.png";
                break;
            case 4:
                result = "https://neilyamit.com/assets/img/random/random-2.jpg";
                break;
            case 5:
                result = "https://spectrum.ieee.org/image/MjkzNzc2Nw.jpeg";
                break;
                default:
                    result = "https://spectrum.ieee.org/image/MjkzNzc2Nw.jpeg";
        }
        return result;
    }

    static String generateName(){
        return "Label".concat(Integer.toString(++labelNumber));
    }
}
