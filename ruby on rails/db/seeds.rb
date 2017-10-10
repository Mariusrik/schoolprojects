# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).


book1desc = "Java Enterprise Edition (Java EE) continues to be one of the leading Java technologies and platforms.
Beginning Java EE 7 is the first tutorial book on Java EE 7. Step by step and easy to follow, this book describes
many of the Java EE 7 specifications and reference implementations, and shows them in action using practical examples.
This definitive book also uses the newest version of GlassFish to deploy and administer the code examples.

\nWritten by an expert member of the Java EE specification request and review board in the Java Community Process (JCP),
this book contains the best information possible, from an expert’s perspective on enterprise Java technologies."

book2desc = 'Android Programming: The Big Nerd Ranch Guide: is an introductory Android book for programmers with Java experience.
    Based on Big Nerd Ranch’s popular Android Bootcamp course, this guide will lead you through the wilderness using
hands-on example apps combined with clear explanations of key concepts and APIs. This book focuses on practical
techniques for developing apps compatible with all versions of Android widely used today (Android 2.2 - 4.2).\n

    Write and run code every step of the way – creating apps that catalog crime scenes, browse photos, track your
jogging route, and more. Each chapter and app has been designed and tested to provide the knowledge and experience
you need to get started in Android development.'

book3desc = 'You want to write professional-grade applications: Rails is a full-stack, open-source web framework,
with integrated support for unit, functional, and integration testing. It enforces good design principles, consistency
of code across your team (and across your organization), and proper release management. But Rails is more than a set
of best practices. Rails makes it both fun and easy to turn out very cool web applications.

Need Ajax support, so your web applications are highly interactive? Rails has it built in. Want an application that sends and receives e-mail? Built in.
Supports internationalization and localization? Built in. Do you need applications with a REST-based interface (so they can
interact with other RESTful applications with almost no effort on your part)? All built-in.'

book4desc = 'In just 24 lessons of one hour or less, you can learn how to create dynamic, interactive Web pages with the
popular and ubiquitous JavaScript web programming language. Using a straightforward, step-by-step approach,
each lesson in this book clearly and carefully walks you through basic concepts and techniques, and helps you learn
the essentials of JavaScript programming from the ground up.'

book5desc = "The C# Player's Guide (3rd Edition) is the ultimate guide for people starting out with C#, whether you
are new to programming, or an experienced vet. This guide takes you from your journey's beginning, through the most
challenging parts of programming in C#, and does so in a way that is casual, informative, and fun. This version of
the book is updated for C# 7.0 and Visual Studio 2017 Get off the ground quickly, with a gentle introduction to C#,
Visual Studio, and a step-by-step walkthrough and explanation of how to make your first C# program. Learn the
fundamentals of procedural programming, including variables, math operations, decision making, looping, methods,
and an in-depth look at the C# type system."



categories_list = [
    'Java',
    'Ruby on Rails',
    'Android',
    'HTML',
    'Javascript',
    'C++',
    'C#'
]

books_list = [
    ['Beginning Java EE 7', book1desc, Rails.root.join("app/assets/images/seed_images/java.jpg").open, [1] ],
    ['The Android Ranch', book2desc, Rails.root.join("app/assets/images/seed_images/android.jpg").open, [1, 3]],
    ['Agile Web Development with Rails', book3desc, Rails.root.join("app/assets/images/seed_images/rails.jpg").open, [2, 4, 5]],
    ['JavaScript in 24 Hours, Sams Teach Yourself', book4desc, nil, [4, 5] ],
    ['The C# Players Guide', book5desc, Rails.root.join("app/assets/images/seed_images/csharp.jpg").open, [7, 6] ]
]

users_list = [
    ['Nils', 'nils@gmail.com', 'nilserbest1', 'admin'],
    ['Jonas', 'jonascoolman@gmail.com', 'sommer1969', 'user'],
    ['Lise', 'lise_l@hotmail.com', 'k0s3b4ms3', 'user'],
    ['Ronny', 'tanks66@gmail.com', 'Sprettert123', 'user'],
    ['Per', 'per@gmail.com', 'pererbest1', 'moderator']
]

ratings_list = [
    [5, 2, 1],
    [3, 1, 1],
    [6, 3, 2],
    [4, 1, 2],
    [5, 5, 3],
    [6, 1, 3],
    [3, 4, 4],
    [5, 3, 4]
]

comments_list = [
    [1, 1, 'Cool book'],
    [5, 4, 'Nice way to learn C#'],
    [1, 3, 'Idk what i feel about this book, its difficult to read'],
    [2, 3, 'Good book to learn Android from scratch'],
    [4, 3, 'Horrible book'],
    [1, 1, 'Great way to learn java enterprise!'],
    [4, 2, 'It probally takes more than 24 hours to finish this, beside that - its a good book']
]

forSale_list = [
    [1, 1, 300, 'Contact me at mail nils@gmail.com'],
    [1, 2, 299, 'Text me at number: 12345678'],
    [4, 3, 800, 'Contact at: lise_l@hotmail.com'],
    [2, 1, 500, 'Contact me at mail nils@gmail.com'],
    [2, 3, 475, 'Contact at: lise_l@hotmail.com'],
]

categories_list.each do |name|
  Category.create( name: name )
end

books_list.each do |title, description, image, categories |
  Book.create( title: title, description: description, image: image, category_ids: categories )

end

users_list.each do |name, email, password_digest, user_profile|
  User.create(name: name, email: email, password_digest: password_digest, user_profile: user_profile)
end

ratings_list.each do |score, book_id, user_id|
  Rating.create(score: score, book_id: book_id, user_id: user_id)
end

comments_list.each do |book_id, user_id, content|
  Comment.create(book_id: book_id, user_id: user_id, content: content)
end

forSale_list.each do |book_id, user_id, price, contact|
  ForSale.create(book_id: book_id, user_id: user_id, price: price, contact: contact)
end