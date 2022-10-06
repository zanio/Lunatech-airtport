# Lunatech Airport Assessment Instuctions

We’d like to invite you to do a code assessment as discussed during your
interview. Please read these instructions carefully to ensure that you implement
everything as expected. *Be sure to do your work on a separate branch and when
you are ready for you code to be reviewed, open a pull request to the main
branch*. This is important as it will signify to use that you've completed the
assessment. After completing the assessment, developers from Lunatech will
review your code and you'll be given the chance to "hand off" the application to
them. You'll give a demo of your app, explain your technical choices, and
discuss the application with them.

The code assessment is not a puzzle; it's not meant to be tricky or confusing.
If you have any questions do not hesitate to contact us, we'll be glad to answer
them.

## Guidelines

  - Approach this assessment as you were developing a real world project, we
    want to see how you conduct your engineering practice (project structure,
    code quality, readability, documentation and so on). GitHub Actions are
    enabled for this repo, so feel free to use it.
  - The one week limit is to be able to give rapid feedback, you can have more
    time, just send us an email. It’s OK if you haven’t completed all the
    requirements, the exercise is meant to start a discussion. Remember:
    Quality over Quantity.
  - When you're complete, make sure all of your code is pushed up to this repo
    and let us know.
  - You're free to use whatever tech stack you're comfortable with, just make
    sure the stack makes sense given the assignment

## The Details
The goal of this assessment is to produce a web application written in Java or
Scala, using data available in this repository. This application cover the
following requirements:

### Query:

For this endpoint, given a country name or country code, get the airports and
runways at each airport of that country. For bonus points, make the test
partial/fuzzy (e.g.  entering 'Zimb' will result in 'Zimbabwe').

### Reports:

This endpoint will return to the user the following reports:
  - 10 countries with the highest number of airports (with count), and countries
    with lowest number of airports.
  - Type of runways (as indicated in the `surface` column) per country.
  - Bonus: Print the top 10 most common runway identifications (indicated in `le_ident`
    column).

## Setup

To speed up the assessment development, we have provided a `docker-compose` file that
will run a [PostgreSQL](https://www.postgresql.org/) instance with all the data loaded,
which you can start with the following command: `docker-compose up`. You can connect to
this instance from your application with the following values:

```
JDBC URL = jdbc:postgresql://localhost:5432/lunatech_airport
Username = postgres
Password = postgres
```

The schema is defined within the file `schema.sql` in the `database-init`
folder. For whatever reason if you want to change anything in the
`docker-compose` or re-use it in your own `docker-compose`, feel free. It's
provided for your convenience.
