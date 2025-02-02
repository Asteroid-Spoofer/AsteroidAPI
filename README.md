# AsteroidAPI

[![](https://jitpack.io/v/SerbanHiro/AsteroidAPI.svg)](https://jitpack.io/#SerbanHiro/AsteroidAPI)

## Overview
A powerful and flexible API for Minecraft plugin development.

## Important Guidelines
- **Never push directly to master branch**
- **Always create a new branch for your changes**
- **Submit changes through pull requests**
- **Pull requests require review before merging**

## Development Workflow
1. Create a new branch for your feature/fix:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit them:
   ```bash
   git add .
   git commit -m Descriptive commit message
   ```
3. Push your branch
   ```bash
   git push origin feature/your-feature-name
   ```
4. Create a pull request on GitHub
5. Wait for review and approval
6. Merge once approved

# Installation
<details>
<summary>Gradle</summary>

```gradle
repositories {
  maven { url 'https://jitpack.io' }
}
  
dependencies {
  implementation 'com.github.Asteroid-Spoofer:AsteroidAPI:Tag'
}
```
</details>

<details>
<summary>Maven</summary>

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
  
<dependency>
    <groupId>com.github.Asteroid-Spoofer</groupId>
    <artifactId>AsteroidAPI</artifactId>
    <version>Tag</version>
</dependency>
```
</details>

# Contributing
- Fork the repository
- Follow the development workflow above
- Keep code clean and documented
- Test your changes thoroughly
- Update documentation if needed

# Branch Naming Convention
- Features: `feature/feature-name`
- Bug fixes: `fix/bug-name`
- Documentation: `docs/what-changed`

# Support
For issues or suggestions, please use GitHub's issue tracker.
