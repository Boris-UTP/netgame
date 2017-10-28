using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using System.Data.Objects.SqlClient;

namespace RESTfulAPI_Gamer.Areas.Api.Models
{
    public class AuthorizedUserRepository
    {

        static BDGamerEntities dataContext = new BDGamerEntities();

        public static IQueryable<User> GetUsers()
        {

            IList<User> users = new List<User>();

            //users.Add(new User() { id = 1, Name = "User1" });
            //users.Add(new User() { id = 2, Name = "User2" });
            //users.Add(new User() { id = 3, Name = "User3" });
            //users.Add(new User() { id = 3, Name = "10|userProvider" });

              string[] queryArray;

            var query = from s in dataContext.Users                   
                        select SqlFunctions.StringConvert((double)s.IdUser).Trim() + "|" + s.UserName;

            queryArray = query.ToArray();

            //IQueryable<string> iqueryable = queryArray.AsQueryable();

            for (int i = 0; i < queryArray.Length;i++ )
            {
                users.Add(new User() { Name = queryArray[i].ToString() });
            }
           


            return users.AsQueryable();

            //List<int> grades = new List<int> { 78, 92, 100, 37, 81 };

            //// Convert the List to an IQueryable<int>.
            //IQueryable<int> iqueryable = grades.AsQueryable();



        }

        public class User
        {

            public int id { get; set; }
            public string Name { get; set; }
            

        }


    }
}