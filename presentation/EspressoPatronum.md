theme: Simple - Modified
slidenumbers: true
autoscale: true
footer: @AdamMc331<br/>#ChicagoRoboto

## Espresso Patronum:<br/>The Magic of the Robot Pattern

### Adam McNeilly: Android Engineer - OkCupid

^ Introduce yourself. Explain the need for robots. Overview of Espresso for new devs before explaining robot pattern and its benefits.

---

# What is Espresso?

---

> Use Espresso to write concise, beautiful, and reliable Android UI tests[^1].

^ Espresso is an automated testing framework built by Google that lets you write code to automate your UI and make sure it behaves as you expect. There are alternatives. Robot pattern applies to alternatives, even though we'll be using Espresso for today's example.

[^1]: https://developer.android.com/training/testing/espresso/index.html

---

# Three Classes To Know

1. ViewMatchers
2. ViewActions
3. ViewAssertions

^ In order to follow along with today's sample, there's three Espresso classes to be familiar with.

---

# ViewMatchers

- `withId(...)`
- `withText(...)`
- `isFocusable()`
- `isChecked()`

^ ViewMatchers are used to help Espresso find a view with certain characterstics, like a specific id, text, or flag like focusable/checked. Espresso will use these to see if a view is on the screen with these conditions, it may throw an exception if it's not.

---

# ViewActions

- `typeText(...)`
- `scrollTo()`
- `swipeLeft()`
- `click()`

^ ViewActions are used to tell Espresso to perform an action on a View. This could range from typing text to an input to clicking a button. This is the fun part that replaces everything you're used to doing manually.

---

# ViewAssertions

- `matches(Matcher)`
- `isLeftOf(Matcher)`
- `doesNotExist()`

^ These are used for the "testing" part of Espresso. Use these to make sure your view matches the characteristics that are expected. Examples include making sure a view is not on the screen, or that a view is displaying a certain piece of text.

---

# Espresso Cheatsheet[^2]

![inline](images/espresso-cheatsheet.png)

^ This is a quick cheatsheet that shows you the various classes involved in Espresso testing and the types of methods that fall within each one. It's a great resource to have while you're learning.

[^2]: https://developer.android.com/training/testing/espresso/cheat-sheet.html

---

# Espresso Example

```kotlin
// onView gives us a ViewInteraction where we can perform an action
// or check an assertion.
onView(ViewMatcher)
	.perform(ViewAction)
	.check(ViewAssertion)
```

^ Walkthrough what's happening here, given what we've learned from the Espresso docs so far.

---

# Espresso Example

```kotlin
// Type into an EditText, verify it appears in a TextView
onView(withId(R.id.etInput)).perform(typeText("Adam"))
onView(withId(R.id.tvOutput)).check(matches(withText("Adam")))
```

^ Walkthrough what's happening here, given what we've learned from the Espresso docs so far.

---

# Sample Project

![inline](images/sample_recording.gif)

^ To demonstrate how the robot pattern saves us time, we'll consider automating this registration form. It has a registration fragment, which is then replaced by a UserProfile fragment.

---

# The Problem 

Before we introduce robots, let's take a look at the problem it solves.

^ I also think it's best to explain the robot pattern by first demonstrating the problem it solves. Let's consider a couple tests you might write for this. Mine - Successful registration, empty field errors, and invalid field errors. Write these tests based on docs, and then talk about why we struggle.

---

# Test Successful Registration

```kotlin
@Test
fun testSuccessfulRegistration() {
    onView(withId(R.id.etFirstName)).perform(typeText("Adam"))
    onView(withId(R.id.etLastName)).perform(typeText("McNeilly"))
    onView(withId(R.id.etEmail)).perform(typeText("amcneilly@okcupid.com"))
    onView(withId(R.id.etPhone)).perform(typeText("1234567890"))
    onView(withId(R.id.registerButton)).perform(click())

    onView(withId(R.id.tvFullName)).check(matches(withText("Adam McNeilly")))
    onView(withId(R.id.tvEmailAddress)).check(matches(withText("amcneilly@okcupid.com")))
    onView(withId(R.id.tvPhoneNumber)).check(matches(withText("(123)-456-7890")))
}
```

^ Walk through each of the steps here, calling out the verbosity. Espresso methods are well named, though.

---

# Test A Missing Field

```kotlin
@Test
fun testMissingEmailError() {
	onView(withId(R.id.etFirstName)).perform(typeText("Adam"))
    onView(withId(R.id.etLastName)).perform(typeText("McNeilly"))
    onView(withId(R.id.etPhone)).perform(typeText("1234567890"))
    onView(withId(R.id.registerButton)).perform(click())

    onView(withId(R.id.etEmail)).check(matches(hasErrorText("Must enter an email address.")))
}
```

^ We always want to negative test our apps too, right? Also pretty straight forward, but might not be immediately obvious what the difference is from our last test.

---

# One More Negative Test

```kotlin
@Test
fun testInvalidEmailError() {
    onView(withId(R.id.etFirstName)).perform(typeText("Adam"))
    onView(withId(R.id.etLastName)).perform(typeText("McNeilly"))
    onView(withId(R.id.etEmail)).perform(typeText("blahblah"))
    onView(withId(R.id.etPhone)).perform(typeText("1234567890"))
    onView(withId(R.id.registerButton)).perform(click())

    onView(withId(R.id.etEmail)).check(matches(hasErrorText("Must enter a valid email address.")))
}
```

^ Not only do we want to verify an error for a missing field, but we might have to check if it's invalid as well. Also straight forward method names, but not obvious what's happening.

---

# All Together

```kotlin
@Test
fun testSuccessfulRegistration() {
    onView(withId(R.id.etFirstName)).perform(typeText("Adam"))
    onView(withId(R.id.etLastName)).perform(typeText("McNeilly"))
    onView(withId(R.id.etEmail)).perform(typeText("amcneilly@okcupid.com"))
    onView(withId(R.id.etPhone)).perform(typeText("1234567890"))
    onView(withId(R.id.registerButton)).perform(click())

    onView(withId(R.id.tvFullName)).check(matches(withText("Adam McNeilly")))
    onView(withId(R.id.tvEmailAddress)).check(matches(withText("amcneilly@okcupid.com")))
    onView(withId(R.id.tvPhoneNumber)).check(matches(withText("(123)-456-7890")))
}

@Test
fun testMissingEmailError() {
    onView(withId(R.id.etFirstName)).perform(typeText("Adam"))
    onView(withId(R.id.etLastName)).perform(typeText("McNeilly"))
    onView(withId(R.id.etPhone)).perform(typeText("1234567890"))
    onView(withId(R.id.registerButton)).perform(click())

    onView(withId(R.id.etEmail)).check(matches(hasErrorText("Must enter an email address.")))
}

@Test
fun testInvalidEmailError() {
    onView(withId(R.id.etFirstName)).perform(typeText("Adam"))
    onView(withId(R.id.etLastName)).perform(typeText("McNeilly"))
    onView(withId(R.id.etEmail)).perform(typeText("blahblah"))
    onView(withId(R.id.etPhone)).perform(typeText("1234567890"))
    onView(withId(R.id.registerButton)).perform(click())

    onView(withId(R.id.etEmail)).check(matches(hasErrorText("Must enter a valid email address.")))
}
```

^ As you can see, this code is incredibly hard to read, and not just because it's small.

---

# The Problem

1. Extremely Verbose & Unreadable
2. Not Easy To Maintain

[.build-lists: true]

^ We've written so much code just to have three tests, and our tests aren't even that readable. They also aren't maintainable, because we have no separation of concerns.

---

# No Separation Of Concerns

![inline](images/mvp_overview.png)

^ In a typical code base you'll have some architecture like MVP, which is used to separate concerns in your app. Model doesn't care about view. Can swap out parts. Not the case with tests.

---

# No Separation Of Concerns

![inline](images/mvp_with_tests.png)

^ Our tests are so closely coupled to the view. As we add more, this really becomes a problem.

---

# No Separation Of Concerns

![inline](images/mvp_with_tests_changed.png)

^ If we change something in our view, we have to go change every test.

---

# Introducing Robots

![inline](images/mvp_with_robot.png)

^ This is where we introduce robots. Robots are the middle man to separate concerns. 

---

# Separation Of Concerns

![inline](images/mvp_with_robot_changed.png)

^ If something in our view changes, all we have to change is our robot. 

---

> Write your automated tests as if you're telling a Quality Assurance Engineer what to do.

^ Before we get to implementation, let's look at the usage. While we do this, think about how you might describe things to QA.

---

# Usage

```kotlin
@Test
fun testSuccessfulRegistration() {
    RegistrationRobot()
            .firstName("Adam")
            .lastName("McNeilly")
            .email("amcneilly@okcupid.com")
            .phone("1234567890")
            .register()
            .assertFullNameDisplay("Adam McNeilly")
            .assertEmailDisplay("amcneilly@okcupid.com")
            .assertPhoneDisplay("(123)-456-7890")
}
```

^ Explain what's happening here, and that all the ugly code was put into its own method which we'll see in a second.

---

# Define ViewMatchers

```kotlin, [.highlight: 4-12]
class RegistrationRobot {

    companion object {
        private val FIRST_NAME_INPUT_MATCHER = withId(R.id.etFirstName)
        private val LAST_NAME_INPUT_MATCHER = withId(R.id.etLastName)
        private val EMAIL_INPUT_MATCHER = withId(R.id.etEmail)
        private val PHONE_INPUT_MATCHER = withId(R.id.etPhone)
        private val REGISTER_INPUT_MATCHER = withId(R.id.registerButton)

        private val FULL_NAME_DISPLAY_MATCHER = withId(R.id.tvFullName)
        private val EMAIL_DISPLAY_MATCHER = withId(R.id.tvEmailAddress)
        private val PHONE_DISPLAY_MATCHER = withId(R.id.tvPhoneNumber)
    }
}
```

^ It's easiest to define each view matcher in a companion object (or a static field if you're using Java), so that they can all be managed in one place and easily changed.

---

# Each Action As A Method

```kotlin
class RegistrationRobot {

    fun firstName(firstName: String): RegistrationRobot {
        onView(FIRST_NAME_MATCHER).perform(clearText(), typeText(firstName), closeSoftKeyboard())
        return this
    }

    fun register(): RegistrationRobot {
        onView(REGISTER_INPUT_MATCHER).perform(click())
        return this
    }

    fun assertFullNameDisplay(fullName: String): RegistrationRobot {
        onView(FULL_NAME_DISPLAY_MATCHER).check(matches(withText(fullName)))
        return this
    }

    ...
}
```

^ We'll use the builder pattern to write a method for each action our robot can perform. The main benefit of using the builder pattern like this is so we can chain the calls together nicely as seen on the next slide.

---

# One Robot Per Screen

```kotlin
@Test
fun testSuccessfulRegistration() {
    RegistrationRobot()
            .firstName("Adam")
            .lastName("McNeilly")
            .email("amcneilly@okcupid.com")
            .phone("1234567890")
            .register()

    UserProfileRobot()
            .assertFullNameDisplay("Adam McNeilly")
            .assertEmailDisplay("amcneilly@okcupid.com")
            .assertPhoneDisplay("(123)-456-7890")
}
```

^ You should write on robot per activity or fragment that you work with. If you ever have a test that passes control from one fragment to another, it might be instinct to have your `register()` method return the new robot. Need to consider if this is /always/ the case.

---

# Negative Test

```kotlin
@Test
fun testMissingEmailError() {
    RegistrationRobot()
            .firstName("Adam")
            .lastName("McNeilly")
            .phone("1234567890")
            .register()
            .assertEmailError("Must enter an email address.")
}
```

^ For completion, here is what a negative test looks like.

---

# Benefits

1. Readibility
2. Maintainability & Separation Of Concerns
3. Tests Become Easier To Write

[.build-lists: true]

^ 1 - Clear english. 2 - Separated concerns. 3 - Now that you have all possible actions defined, it becomes a lot easier to write all of the possible permutations.

---

# What Else?

^ So all of this is great, but it's not necessarily Espresso specific either. Let's go into some of the benefits we can get with Espresso. One of those is test reporting with screenshots, we'll talk a little bit about what and why, and then circle back to how and why it relates to the Robot pattern.

---

# Better Test Reporting Using Spoon & Falcon

 - Spoon[^3] will run all of our instrumentation tests and build us a static HTML report at the end.
 - Falcon[^4] takes better screenshots, and has a `SpoonCompat` library for the best of both worlds.

^ We can leverage a tool like Spoon to make more effective test reports. Spoon is a library built and maintained by Square.

[^3]: https://github.com/square/spoon

[^4]: https://github.com/jraska/Falcon/

^ Spoon is a test reporting library built by Square that also allows us to take screenshots. Falcon takes better screenshots because it can capture dialogs though. If you want to learn more about the differences, I recommend Sam Edwards DCNYC '16 talk.

---

![inline](images/espresso-spoon.png)

^ Bear in mind, this may not always be the most Google friendly tool, so remember to add other keywords.

---

# Example Spoon Report

![inline](images/test_invalid_email_error.png)

^ This is an example of what a Spoon report would look like. What you're seeing here is our invalid email test with a screenshot of each step along the way. If I hover over a picture I'll see a description of that step. The real benefits of this are the clear steps and human readable format, which we go more into later.

---

# When To Take Screenshots

* After assertions
* After actions - unless that action leads to another screen
* On failure

[.build-lists: true]

^ If we're going to introduce screenshots to our tests, let's talk about when we would take them. 

---

# Why screenshots?

* Human readable output
* See exactly how things were tested
* Diagnose failures quicker

[.build-lists: true]

^ Why are screenshots important/why did I feel necessary to add them in here? How do they enhance what we've already talked about.

---

# Console Output

![inline](images/console_output.png)

^ Previously, we'd get a console output like this from Android Studio, which is really only accessible (and readable) by developers.

---

# Spoon Output

![inline](images/spoon_overview.png)

^ A spoon report, however, is very visual, not tech heavy, and can be shared amongst developers/QA/etc.

---

# See Steps Taken

![inline](images/successful_registration.png)

^ The console output we have told us that we tested registration successfuly, but it never told us how.

---

# Diagnose Failures - Stack Trace

![inline](images/fullname_error.png)

^ Using the old method, we were stuck debugging things with a stack trace.

---

# Diagnose Failures - Clear Image

![inline](images/incorrect_full_name.png)

^ However, if I take a screenshot automatically each time a test fails, I can figure out pretty instantly what happened.

---

# Learn More[^5]

![inline](images/sam_edwards_droidcon.png)

[^5]: https://www.youtube.com/watch?v=fhx_Ji5s3p4

^ If you want to learn more about screenshots/why/the different tools, check out this talk by Sam Edwards.

---

# Adding Screenshots To Our Robot

```kotlin, [.highlight: 2-3, 8-9, 14-15, 17]
fun firstName(firstName: String): RegistrationRobot {
    onView(FIRST_NAME_INPUT_MATCHER).perform(clearText(), typeText(firstName), closeSoftKeyboard())
    takeScreenshot(spoon, "first_name_entered")
    return this
}

fun register(): RegistrationRobot {
    takeScreenshot(spoon, "register_clicked")
    onView(REGISTER_INPUT_MATCHER).perform(click())
    return this
}

fun setFailureHandler(spoon: SpoonRule, context: Context) {
    Espresso.setFailureHandler { error, viewMatcher ->
        takeScreenshot(spoon, "test_failed")
        DefaultFailureHandler(context).handle(error, viewMatcher)
    }
}
```

^ Since we already have each step broken out into a method in our robot, we can just add a screenshot to each one with a description. By this effect, we're getting all of these extra benefits I just discussed, with minimal effort because we've already designed our tests with an architecture optimized for this sort of thing.

---

# Why did we need a robot?

```kotlin
@Test
fun testSuccessfulRegistration() {
    onView(withId(R.id.etFirstName)).perform(typeText("Adam"))
    takeScreenshot(spoon, "first_name_entered")
    onView(withId(R.id.etLastName)).perform(typeText("McNeilly"))
    takeScreenshot(spoon, "last_name_entered")
    onView(withId(R.id.etEmail)).perform(typeText("amcneilly@okcupid.com"))
    takeScreenshot(spoon, "email_entered")
    onView(withId(R.id.etPhone)).perform(typeText("1234567890"))
    takeScreenshot(spoon, "phone_entered")
    takeScreenshot(spoon, "register_clicked")
    onView(withId(R.id.registerButton)).perform(click())

    onView(withId(R.id.tvFullName)).check(matches(withText("Adam McNeilly")))
    takeScreenshot(spoon, "full_name_displayed")
    onView(withId(R.id.tvEmailAddress)).check(matches(withText("amcneilly@okcupid.com")))
    takeScreenshot(spoon, "email_displayed")
    onView(withId(R.id.tvPhoneNumber)).check(matches(withText("(123)-456-7890")))
    takeScreenshot(spoon, "phone_displayed")
}
```

^ Consider this monstrosity, and how even less maintainable that is. 

---

# Let's Add To It

> Your manager just came by and asked for an email opt in field.

![inline](images/opt_in_preview.png)

^ Let's show how easily we can extend this. Consider the example where your manager wants to add some opt in field. 

---

# RegistrationRobot

Only requires one new method on registration.

```kotlin
fun emailOptIn(): RegistrationRobot {
    onView(OPT_IN_MATCHER).perform(click())
    takeScreenshot(spoon, "opted_in")
    return this
}
```

---

# UserProfileRobot

Only needs to consider each state.

```kotlin
fun assertOptedIn(): UserProfileRobot {
    onView(EMAIL_OPT_IN_DISPLAY_MATCHER).check(matches(isChecked()))
    takeScreenshot(spoon, "assert_email_opt_in")
    return this
}

fun assertOptedOut(): UserProfileRobot {
    onView(EMAIL_OPT_IN_DISPLAY_MATCHER).check(matches(isNotChecked()))
    takeScreenshot(spoon, "assert_email_opt_out")
    return this
}
```

---

# Test

Test now only requires two really quick add ons to consider.

```kotlin, [.highlight: 8, 15]
@Test
fun testSuccessfulRegistrationWithOptIn() {
    RegistrationRobot(spoon)
            .firstName("Adam")
            .lastName("McNeilly")
            .email("amcneilly@okcupid.com")
            .phone("1234567890")
            .emailOptIn()
            .register()

    UserProfileRobot(spoon)
            .assertFullNameDisplay("Adam McNeilly")
            .assertEmailDisplay("amcneilly@okcupid.com")
            .assertPhoneDisplay("(123)-456-7890")
            .assertOptedIn()
}
```

---

# Recap

1. Use robots to solve separation of concerns problem
2. Makes your tests more readable and fun to write
3. Leverage this for better reporting
4. This is not specific to Espresso/Spoon/Falcon. 

[.build-lists: true]

^ Re: 4, this is just like any other pattern. It can be applied to a number of tools that have the same problem.

---

# Contact

* Adam McNeilly - OkCupid (We're Hiring!)
* Twitter - @AdamMc331
* https://github.com/AdamMc331/EspressoPatronum

^ This will take you to a sample project that uses the robot pattern and Spoon for testing. The README will have a link to the various resources used throughout this as well.