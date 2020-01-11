# CS 675A: Assignment 1

## Getting Started

You can build this repository by using sbt.

    $ sbt
    sbt:main> compile

You can run the main function (in src/main/scala/Main.scala) by using the run command.

    $ sbt
    sbt:main> run

You can run the tests (in src/test/scala) by using the test command.

    $ sbt
    sbt:main> test

Typically, one starts sbt once and keeps it running while the program is being developed and uses these commands (compile, run, test, etc.) repeatedly within a single sbt session.

## Instructions

Clone (**do not download, make sure you clone**) this repository and make regular commits to it as you develop your solution. You will need to push your commits to a clone of this repository at the time of submission.

Make sure that all your changes are confined to src/main/scala/Evaluation.scala. This is the only file that we will grade. If you make changes in some other file and that causes our tests to not compile or fail, you will get very little credit. 

We will post instructions on how to use our autograder in a couple of days. 

## General Advice

Work incrementally, do not try to write the entire program at once. First, write a few simple test cases, and the simplest possible implementation of the functions. Make sure your code runs for the simple test cases. Once these are fully working, progress to the more complex test cases. 

Some of the harder test cases will need algorithmic improvements.

Please write the code entirely on your own. We will be very strict with any cases of plagiarism.

