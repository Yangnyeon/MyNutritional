package com.example.nutritionalrecom.diet_Food

class Food_Model(var Food_Name : String?, var Food_Image: String?, val Food_Doc: String?, val Food_Kcal: Long?, Food_ServingSize : Long?)
{
   /* constructor() : this(
        "",
        "",
        "",
        ""
    )*/
    constructor() : this(
        "",
        "",
       "",
       0,
       0
    )


}