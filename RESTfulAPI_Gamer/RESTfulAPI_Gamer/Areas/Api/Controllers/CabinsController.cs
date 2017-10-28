using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

using RESTfulAPI_Gamer.Areas.Api.Models;
using System.Web.Http.Controllers;
using System.Net.Http;

namespace RESTfulAPI_Gamer.Areas.Api.Controllers
{
    public class CabinsController : Controller
    {
 
        
        private CabinsRepository cabinsRepository;
        RSAProvider RSACryptoServiceProvider = new RSAProvider();
  
        RSAProvider rsaProvider = new RSAProvider();

        public CabinsController()
        {

            cabinsRepository = new CabinsRepository();
        
        }

        [HttpGet]
        public JsonResult GetAllUbicationsCabins()
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
            
            
            return Json(cabinsRepository.GetAllUbicationsCabins(), JsonRequestBehavior.AllowGet);
        }



        public JsonResult Cabin(int? id, Cabin item)
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
                    return Json(cabinsRepository.InsertCabin(item));

                case "PUT":
                    return Json(cabinsRepository.UpdateCabin (item));

            }

            return Json(new { Error = true, Message = "Operacion HTTP desconocida" });

        }





    }
}
