## Setting up and getting started

### Setting up the project in your computer

Ensure that you have the following installed: 
* JDK 11.
* IntelliJ IDE (highly recommended).

Firstly, **fork** this repo and **clone** a copy into your computer.

If you plan to use Intellij IDEA: 
1. **Ensure IntelliJ is configured to use JDK 11**.
    1. Click on `Configure` > `Structure for New Projects` > `Project Settings` > `Project`, 
       and ensure the `Project SDK` is using **JDK 11**.
2. **Import the project as a Gradle project**.
    1. Click on `Import Project` and locate the `build.gradle` file and select it. Click `OK`
    2. If asked, choose to `Open as Project` (not `Open as File`).
    3. Click `OK` to accept the default settings but do ensure that the 
       selected version of `Gradle JVM` matches the JDK being used for the project.
    4. The import process could take a few minutes to finish.
3. **Verify the setup**: 
    1. Run the `seedu.anichan.Main` and try a few commands. 
    2. You may want to refer to our [User Guide](UserGuide.md) for the list of commands.

----

### Before writing code
1. **Configuring the coding style**

    If using IDEA, follow this guide 
    [IDEA: Configuring the code style](https://se-education.org/guides/tutorials/intellijCodeStyle.html) 
    to setup IDEA’s coding style to match ours.

2. **Set up CI**

    There is no set up required as the project comes with a GitHub Actions config files, 
    located in `.github/workflows` folder. When GitHub detects these files, it will run the CI for the project
    automatically at each push to the master branch or to any PR.

3. **Learn the design**

    When you are ready to start writing codes, 
    we recommended that you have a look at AniChan's overall design 
    by reading about [AniChan's architecture](DeveloperGuide.md#21-architecture).
