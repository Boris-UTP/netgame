using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using RESTfulAPI_Gamer.Areas.Api.Models;


namespace RESTfulAPI_Gamer.Areas.Api.Controllers
{
    public class AssistancesController : Controller

    {
        private AssistanceRepository assistanceRepository;
        RSAProvider RSACryptoServiceProvider = new RSAProvider();


        public AssistancesController()
        {

            assistanceRepository = new AssistanceRepository();
        
        }


        public JsonResult Assistance(int? id, Assistance item)
        {


            string token = "";

            try
            {
                //Usuario desencriptado:  10|userProvider
                //Token: CEi5AtrgdYfEXmx9OIt/gOYF7ruWaUbRLOZYeGufYboB0gYUCS5xRs2pBwHVHNIlZAMcAWpb1kIWn9AuQollIZmgxAKaL71VJonLSWvtFvjz+DZo5Sg4aU6Atd8DMK6/90r6PV/5dw/1MS17JsUQwpkBU/iFMevheszJKWlJdLI=

                token = this.Request.Headers.GetValues("Token").First();
            }
            catch (Exception)
            {
                string tokens = "Missing Token";
                return Json(tokens, JsonRequestBehavior.AllowGet);
            }

            try
            {   // busqueda de usuario en el repositorio
                AuthorizedUserRepository.GetUsers().First(x => x.Name == RSACryptoServiceProvider.DecripText(token));

            }
            catch (Exception)
            {
                return Json("Unauthorized User", JsonRequestBehavior.AllowGet);
            }



            switch (Request.HttpMethod)
            {
                case "POST":
                    return Json(assistanceRepository.InsertAssistance(item));           
            }

            return Json(new { Error = true, Message = "Operacion HTTP desconocida" });


        }



    }
}
