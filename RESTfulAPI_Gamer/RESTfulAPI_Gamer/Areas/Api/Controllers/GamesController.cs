using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

using RESTfulAPI_Gamer.Areas.Api.Models;


namespace RESTfulAPI_Gamer.Areas.Api.Controllers
{
    public class GamesController : Controller
    {
       
        private GamesRepository clientesManager;
        RSAProvider RSACryptoServiceProvider = new RSAProvider();


        public GamesController()
        {

            clientesManager = new GamesRepository();
        
        }

        [HttpGet]
        public JsonResult Games()
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

            return Json(clientesManager.GetAllGames(), JsonRequestBehavior.AllowGet);

        }




    }
}
