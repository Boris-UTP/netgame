using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using RESTfulAPI_Gamer.Areas.Api.Models;

namespace RESTfulAPI_Gamer.Areas.Api.Controllers
{
    public class GamersController : Controller
    {


         private GamersRepository gamersRepository;
         RSAProvider RSACryptoServiceProvider = new RSAProvider();

         public GamersController()
        {

            gamersRepository = new GamersRepository();
        
        }


         public JsonResult Gamer(int? id, Gamer item)
         {

             string token = "";

             try
             {                
                 token = this.Request.Headers.GetValues("Token").First();
             }
             catch (Exception)
             {
                 string tokens = "Missing Token";
                 return Json(tokens, JsonRequestBehavior.AllowGet);
             }

             try
             {  
                 AuthorizedUserRepository.GetUsers().First(x => x.Name == RSACryptoServiceProvider.DecripText(token));

             }
             catch (Exception)
             {
                 return Json("Unauthorized User", JsonRequestBehavior.AllowGet);
             }

             switch (Request.HttpMethod)
             {
                 case "POST":
                     return Json(gamersRepository.InsertGamer(item));

                 case "PUT":
                     return Json(gamersRepository.UpdateGamer(item));

             }

             return Json(new { Error = true, Message = "Operacion HTTP desconocida" });


         }



    }
}
