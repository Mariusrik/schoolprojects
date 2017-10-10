# PG4300 Ruby on Rails - Syllabus book web application
This project is the main exam in our course PG4300 Ruby on Rails on Westerdals Oslo ACT. We got as a task to create a web application using Ruby on Rails. The task was completely open but with some requirements. We decided to make a book website where students could register themselves, then create books of interest - both study books and novels. Students can also comment books, rate them and share information about selling the books (between students).

### Heroku link
[Our Heroku page](https://ruby-book-project.herokuapp.com/ "Our Heroku")

### Site users
We created premade users using seed, these login info can be found in the seed file. Here is three of them with different roles:

Role | Mail | Password
--- | --- | ---
admin | nils@gmail.com | nilserbest1
moderator | per@gmail.com | pererbest1
user | jonascoolman@gmail.com | sommer1969

## Getting Started
Due to one of the requirements that was to use Docker with this project, it should be quite easy to get it up and running on your own. Just follow these steps.

### Prerequisites
* General knowledge about Docker and how to install this yourself
* System dependencies
None, beside Docker dependencies

### Installing
* Download Docker and complete it's installation

#### Configuration
Noone :) 

#### Deployment instructions
To deploy the application on your local machine, use commandpromt/bash to locate your project folder. Then make sure port 3000 is free to use, if not change the port in docker-compose.yml. From inside the folder issue these commands
```
  docker-compose build
```
Following a successfull build, run 
```
  docker-compose up
```

#### Database creation
The following command needs to be ran from another prompt than where the docker-images was ran, but still from inside the project folder.
```
  docker-compose run web rails db:create
```

#### Database initialization
```
  docker-compose run web rails db:migrate db:seed
```

#### How to run the tests
```
  docker-compose run web rails test
```

## Project report
Under comes a description of how we worked with different parts of the project. And the result from this.

* **Database**
In the project, we have used Postgres database run in own image on our computers. And on our Heroku uploaded site Heroku has also provided with Postgres. Our tests are running at a local sqlite database.

[Link to databasemodel (Without the automaticly created id's)](https://00e9e64bac4b4cb047fbdd4c78c23f2d7d4841c2331b0f3fb9-apidata.googleusercontent.com/download/storage/v1/b/pg4300grp10b/o/direct-uploads%2Ftable.png?qk=AD5uMEup7tQurBjw9U66KAG2VvK7Y81kwkWLKMPgfDr58R6QrVpK1jqaMqtPdoQPo1wjBPVbYydCI32vi2XxOoEGT-Ayqi8vujN92Pi7RN2stMPXj2R4a144jZ4v_ZcFwNIihVy-RC5rgmHsTg5khai3lqP_zsdnazn4SKY6-LhelUxEfpCa8RPPVstFoAw7XTdIyaBrkMQEkB3n1yzqgvDQibcd6UwutXNgMKOeHXwQ_WK9oFNxwuNupejICHUfeaWKqNCCi30gQIQCqIGQlV8WCWUYd6Prc0_jtEWqStX2yU0to4Aai9ZhW4Cu_3Je3Rlw3qoeT6pmlDNWPeqKNbS-hPSo0oamfXHss88wILsD1FVeuyBI-cq9rJVGW42SYVsZXg3Qret0HM0o0Pqqg_wYQlrIXLYzfkbZuJe-LU1LvTMD9kkl_WOjMAH3C-3IHQm-fw81a-Bs97ihFx8iG2omGb1xjVptzKedpJa1I_f6Jst_TjXoxhC1KpzaX_cATQOm2qP_6Wn-OLSnueNffI6dOc8t5GXxMWj1KojpKwwlkwiAZMeKLi0118KqD82XnV4Im_f86XfClE76rYE81VPSbH9_WrOUdPkVeylWknY8cy0DuDU31zr_m5CpzoPUVSW0FQmuXP2PnfwG0Bfp27FOx6xXkaqO_4Dva2G0CBfNmYKGWimjzRCgiX5T62ojleKrHhuTC67hhSIJZrzZRsIKIDNYj-izyIMW-JLbjXmnTBQ6q2lhgqx-Or6k_o_0ztVxXIvRLki_S5tkoY-bNMJrMMG7o4lDiBujH3iXuopdcFVVcIVZ-WQ "Databasemodel")

* **Controllers**
We have implemented a total of 9 controllers divided on these 7 tables. The two others is one created (custom) for welcome page and one for session.
* **Use of Docker**
From the beginning of the project we decided we wanted both Docker and Heroku up and running on all members computers. Both rails and the Postgres database runs as images. This is how our docker-compose.yml looks like:
```
version: '2'
services:
 db:
   image: postgres
 web:
   environment:
     PORT: 3000
   build: .
   container_name: pg4300grp10
   command: bundle exec rails s -p 3000 -b '0.0.0.0'
   volumes:
     - .:/myapp
   ports:
     - "3000:3000"
   depends_on:
     - db
```

* **Use of Heroku**
Same as with Docker, we used Heroku from the beginning. Our Heroku is connected to Git and automatically updates based on Git.
* **AJAX**
We use AJAX to automatically update the comment section once an user have posted a post.
* **Use of propper CSS and Javascript**
We have used Bootstrap to make the site prettier. This makes the site friendly on both big pc-screens and smaller mobile screens. It also gives us nice opportunities like dropdown menus etc.
* **Hashing and salting**
When user chooses a password on creation or updating, the salt and password will be mixed before it gets hashed. The hashed value and the salt is saved in the database. 
```
class User < ApplicationRecord
  ...
  private
  def encrypt_password
    if password_digest.present?
      self.password_salt = BCrypt::Engine.generate_salt
      self.password_digest = BCrypt::Engine.hash_secret(password_digest, password_salt)
    end
  end
 ```
* **Testing**
We used quite a lot time on writing tests, the model tests are quite complete with good coverage. The integration tests were more difficult and we only managed to write some. Also tried to write some system tests in browser, but that kind of failed.
* **Login**
It is possible to login and logout. When logging in its created a new session for you.
```
class SessionsController < ApplicationController
...
  def create
    user = User.authenticate(params[:email], params[:password_digest])
    if user
      reset_session
      session[:user_id] = user.id
      redirect_to home_path, :success => 'Logged in!'
    else
      redirect_to home_path, :warning => 'Invalid email or password'
    end
  end
```

* **Different user rights**
We have implmented three different usergroups: user, moderator and admin. 
One example of the diffrence of rights between user and admin is in book/1 (show.html) where only admin can edit a book but everyone can go back.
```
  <% if current_admin %>
  <%= link_to 'Edit', edit_book_path(@book) %> |
  <% end %>
  <%= link_to 'Back', _back_url %>
```
* **Search**
We have implemented a search function that allows you to search for books based on title. This search is not case-sensitive. The results will show on the frontpage.

* **Categories has and belongs to many books**
A book must have one and can have many categories, meanwhile a category can have many books. This creates a many-to-many relationship which we solved in the following way:
```
schema.rb:
  create_table "books", force: :cascade do |t|
    t.string "title"
    t.string "description"
    t.string "image"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end
  create_table "books_categories", id: false, force: :cascade do |t|
    t.bigint "book_id", null: false
    t.bigint "category_id", null: false
    t.index ["book_id", "category_id"], name: "index_books_categories_on_book_id_and_category_id"
    t.index ["category_id", "book_id"], name: "index_books_categories_on_category_id_and_book_id"
  end
  create_table "categories", force: :cascade do |t|
    t.string "name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end
  ```

* **Validation**
All models have been given validations. These focus on a width specter of validations, from length and uniqueness to format and confirmation. Most of these has also been tested with models testing. Example of this validation is:
```
class Book < ApplicationRecord
  ...
  validates_presence_of :title, :description, :categories
  validates_uniqueness_of :title
  validates_length_of :title, :maximum => 80
```

## Used libraries/sources/gems
Following is a list over gems and other sources used in our project, with a short description of what they have been used for.

### Pagination
Used to limit the number of books shown at frontpage. This example sets the max number of books to 3.
```
class WelcomeController < ApplicationController
  skip_before_action :require_login, :only => [:index]
def index
  @books = Book.search(params[:search]).searchcategories(params[:searchcategories]).paginate(:page => params[:page])
end
  WillPaginate.per_page = 3
end
```

### Carrierwave
Dealing with the imageupload from form on webpage to Google Cloud.

### Google-api-client
Due to Heroku working on different dynos, dyno will reset on every new upload to Heroku - which will reset all previous updated data. To prevent this we only save a link in user's table to an image location saved on Google Cloud. The config for that looks like this:
```
CarrierWave.configure do |config|
  config.fog_provider = 'fog/google'
  config.fog_credentials = {
      ...
  }
  config.fog_directory = 'pg4300grp10b'
end
```

## Authors
* **Andreas Ødegaard**
* **Marius Rikheim**
* **Thomas René Gabrielsen**

## Individual Contribution

**Andreas Ødegaard - github: odeand14**

I have been working with the project in general, creation of the Docker container deployment to Heroku and maintaining the Heroku and git repos. I have also contributed to creating tables and page functionality. I have done the AJAX calls for comments and ratings, made the rating system, join table and functionality for books multi categories, login/session functionality and user permissions. Some styling on main page, and created login/sell book modals. 

**Marius Rikheim - github: rikmar15**

I have mainly worked with style, navigation and pagination. I have also made rails warnings with individual tags for style. Created search functionality and sorting by tag. In addition to adding bootstrap, making it work with rails, and generally fixing bootstrap issues.  

**Thomas Gabrielsen - github: gabtho15**

Early on my focus was together with the others on planning, scaffolding and creating tables. Beside that I have focused on testing and fixed the image upload with Google Cloud. I have also done some minor fixes here and there and started on the for_sale functionality. At the end I had mainfocus on the readme/report at git and going over validation for the models. 


## Acknowledgments
Book images and description is borrowed from their respective book-pages at Amazon. 
* [Beginning Java EE 7](https://www.amazon.com/Beginning-Java-EE-Expert-Voice/dp/143024626X)
* [The Android Ranch](https://www.amazon.com/Android-Programming-Ranch-Guide-Guides/dp/0321804333)
* [Agile Web Development with Rails 5 ](https://www.amazon.com/Agile-Web-Development-Rails-5/dp/1680501712)
* [JavaScript in 24 Hours, Sams Teach Yourself](https://www.amazon.com/JavaScript-Hours-Sams-Teach-Yourself/dp/0672336081)
* [he C# Player's Guide (3rd Edition)](https://www.amazon.com/C-Players-Guide-3rd/dp/0985580135)
