using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;

namespace RESTfulAPI_Gamer
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );


           // config.Routes.MapHttpRoute(
           //"Acceso Cliente2",
           //"Apo/Cliente/Get/{id}",
           //new { Controller = "Cliente", action = "Get", id = RouteParameter.Optional }
           //);


        }
    }
}
