using System.Web.Mvc;

namespace RESTfulAPI_Gamer.Areas.Api
{
    public class ApiAreaRegistration : AreaRegistration
    {
        public override string AreaName
        {
            get
            {
                return "Api";
            }
        }

        public override void RegisterArea(AreaRegistrationContext context)
        {
 
            context.MapRoute(
                "Acceso Cliente",
                "Api/Clientes/Cliente/{id}",
                new { Controller = "Clientes", action = "Cliente", id = UrlParameter.Optional }
            );

            context.MapRoute(
                 "Acceso Clientes",
                 "Api/Clientes",
                 new { Controller = "Clientes", action = "Clientes" }
            );


            context.MapRoute(
                 "Acceso Cliente2",
                  "Api/Cliente/Get/{id}",
                 new { Controller = "Cliente", action = "Get", id = UrlParameter.Optional }
             );

            context.MapRoute(
               "Acceso Game",
               "Api/Games/Cliente/{id}",
               new { Controller = "Games", action = "Cliente", id = UrlParameter.Optional }
           );

            context.MapRoute(
                 "Acceso Gamer",
                 "Api/Games",
                 new { Controller = "Games", action = "Games" }
            );


           context.MapRoute(
               "Acceso Usuario",
               "Api/Users/User/{id}",
               new { Controller = "Users", action = "User", id = UrlParameter.Optional }
           );

            context.MapRoute(
                 "Acceso usu p",
                 "Api/Users/Users/{UserName,Password}",
                 new { Controller = "Users", action = "Users" }
            );


            context.MapRoute(
             "Acceso Publication",
             "Api/Publications/Publication/{id}",
             new { Controller = "Publications", action = "Publication", id = UrlParameter.Optional }
             );

             context.MapRoute(
                    "Acceso Publications",
                    "Api/Publications/Publications/{idPublication}",
                    new { Controller = "Publications", action = "Publications" }
               );


              context.MapRoute(
               "Acceso Eventos",
               "Api/Event",
               new { Controller = "Events", action = "GetAllEvents" }
               );

              context.MapRoute(
               "Acceso Evento",
               "Api/Events/Event/{id}",
               new { Controller = "Events", action = "Event", id = UrlParameter.Optional }
               );

              context.MapRoute(
                "Acceso Cabins",
                "Api/Cabins",
                new { Controller = "Cabins", action = "GetAllUbicationsCabins" }
                );


                 context.MapRoute(
                 "Acceso Cabin",
                 "Api/Cabins/Cabin/{id}",
                 new { Controller = "Cabins", action = "Cabin", id = UrlParameter.Optional }
                 );


              context.MapRoute(
                "Acceso Assistance",
                "Api/Assistances/Assistance/{id}",
                new { Controller = "Assistances", action = "Assistance", id = UrlParameter.Optional }
                );


              context.MapRoute(
                 "Acceso Gamer1",
                 "Api/Gamers/Gamer/{id}",
                 new { Controller = "Gamers", action = "Gamer", id = UrlParameter.Optional }
                 );

            context.MapRoute(
               "Api_default",
               "Api/{controller}/{action}/{id}",
                new { action = "Index", id = UrlParameter.Optional }
            );



        }
    }
}
