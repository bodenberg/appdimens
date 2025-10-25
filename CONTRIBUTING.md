---
layout: default
title: "🤝 Contributing to AppDimens"
---

# 🤝 Contributing to AppDimens

Thank you for your interest in contributing to AppDimens! This document provides guidelines and information for contributors.

> **Languages:** English | [Português (BR)](LANG/pt-BR/CONTRIBUTING.md) | Español

> **Note:** Spanish translation is not yet available.

## 📋 Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [Getting Started](#getting-started)
3. [Development Setup](#development-setup)
4. [Contributing Guidelines](#contributing-guidelines)
5. [Pull Request Process](#pull-request-process)
6. [Issue Guidelines](#issue-guidelines)
7. [Documentation](#documentation)
8. [Testing](#testing)
9. [Release Process](#release-process)

## 📜 Code of Conduct

This project adheres to a code of conduct. By participating, you are expected to uphold this code. Please report unacceptable behavior through our private contact channel.

## 🚀 Getting Started

### Prerequisites

- **Android Development**: Android Studio, JDK 11+, Android SDK
- **iOS Development**: Xcode 12+, Swift 5.0+, iOS 13.0+
- **General**: Git, GitHub account

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/your-username/appdimens.git
   cd appdimens
   ```
3. Add the upstream repository:
   ```bash
   git remote add upstream https://github.com/bodenberg/appdimens.git
   ```

## 🛠 Development Setup

### Android Setup

1. **Open in Android Studio**:
   ```bash
   cd Android
   # Open Android Studio and import the project
   ```

2. **Install Dependencies**:
   ```bash
   ./gradlew build
   ```

3. **Run Tests**:
   ```bash
   ./gradlew test
   ```

### iOS Setup

1. **Open in Xcode**:
   ```bash
   cd iOS
   open AppDimens.xcodeproj
   ```

2. **Install Dependencies**:
   - CocoaPods: `pod install`
   - Swift Package Manager: Automatic

3. **Run Tests**:
   - In Xcode: Product → Test (⌘+U)

## 📝 Contributing Guidelines

### Types of Contributions

We welcome several types of contributions:

- 🐛 **Bug Fixes**: Fix existing issues
- ✨ **New Features**: Add new functionality
- 📚 **Documentation**: Improve documentation
- 🧪 **Tests**: Add or improve tests
- 🎨 **Examples**: Add usage examples
- 🔧 **Performance**: Optimize performance
- 🌐 **Translations**: Add language support

### Development Workflow

1. **Create a Branch**:
   ```bash
   git checkout -b feature/your-feature-name
   # or
   git checkout -b fix/issue-number
   ```

2. **Make Changes**:
   - Follow the coding standards
   - Add tests for new functionality
   - Update documentation as needed

3. **Test Your Changes**:
   ```bash
   # Android
   ./gradlew test
   ./gradlew lint
   
   # iOS
   # Run tests in Xcode
   ```

4. **Commit Your Changes**:
   ```bash
   git add .
   git commit -m "feat: add new responsive dimension type"
   ```

### Coding Standards

#### Android (Kotlin)

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Add KDoc comments for public APIs
- Follow the existing code style

```kotlin
/**
 * Calculates responsive dimensions based on screen characteristics.
 * 
 * @param baseValue The base dimension value
 * @param screenType The screen type to use for calculations
 * @return The calculated responsive dimension
 */
fun calculateDimension(baseValue: Float, screenType: ScreenType): Float {
    // Implementation
}
```

#### iOS (Swift)

- Follow [Swift API Design Guidelines](https://swift.org/documentation/api-design-guidelines/)
- Use meaningful variable and function names
- Add documentation comments for public APIs
- Follow the existing code style

```swift
/// Calculates responsive dimensions based on screen characteristics.
/// 
/// - Parameters:
///   - baseValue: The base dimension value
///   - screenType: The screen type to use for calculations
/// - Returns: The calculated responsive dimension
func calculateDimension(baseValue: CGFloat, screenType: ScreenType) -> CGFloat {
    // Implementation
}
```

### Commit Message Format

Use conventional commits format:

```
type(scope): description

[optional body]

[optional footer]
```

**Types**:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

**Examples**:
```
feat(android): add new scaling algorithm for tablets
fix(ios): resolve memory leak in dimension calculations
docs: update installation guide with new examples
test(android): add unit tests for conditional scaling
```

## 🔄 Pull Request Process

### Before Submitting

1. **Update Documentation**: Update relevant documentation
2. **Add Tests**: Add tests for new functionality
3. **Update Examples**: Add examples if applicable
4. **Check Compatibility**: Ensure changes work across supported platforms
5. **Performance**: Consider performance implications

### Pull Request Template

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Performance improvement
- [ ] Breaking change

## Testing
- [ ] Tests pass locally
- [ ] New tests added for new functionality
- [ ] Manual testing completed

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] No breaking changes (or documented)
```

### Review Process

1. **Automated Checks**: CI/CD pipeline runs automatically
2. **Code Review**: Maintainers review the code
3. **Testing**: Changes are tested on multiple devices
4. **Approval**: At least one maintainer approval required

## 🐛 Issue Guidelines

### Before Creating an Issue

1. **Search Existing Issues**: Check if the issue already exists
2. **Check Documentation**: Review relevant documentation
3. **Test Latest Version**: Ensure you're using the latest version

### Bug Reports

Use the bug report template:

```markdown
## Bug Description
Clear description of the bug

## Steps to Reproduce
1. Step one
2. Step two
3. Step three

## Expected Behavior
What should happen

## Actual Behavior
What actually happens

## Environment
- Platform: Android/iOS
- Version: X.X.X
- Device: [Device model]
- OS Version: [OS version]

## Additional Context
Any additional information
```

### Feature Requests

Use the feature request template:

```markdown
## Feature Description
Clear description of the feature

## Use Case
Why is this feature needed?

## Proposed Solution
How should this feature work?

## Alternatives Considered
Other solutions you've considered

## Additional Context
Any additional information
```

## 📚 Documentation

### Documentation Standards

- **Clear and Concise**: Write clear, easy-to-understand documentation
- **Examples**: Include practical examples
- **Up-to-date**: Keep documentation current with code changes
- **Consistent**: Follow the established documentation style

### Documentation Types

1. **API Documentation**: Code comments and inline documentation
2. **User Guides**: Installation and usage guides
3. **Examples**: Practical usage examples
4. **Architecture**: Technical architecture documentation

### Updating Documentation

When making code changes:

1. **Update API Documentation**: Update relevant code comments
2. **Update User Guides**: Update installation and usage guides
3. **Add Examples**: Add examples for new features
4. **Update README**: Update main README if needed

## 🧪 Testing

### Testing Requirements

- **Unit Tests**: Add unit tests for new functionality
- **Integration Tests**: Test integration with existing code
- **Platform Tests**: Test on both Android and iOS
- **Device Tests**: Test on different device types and screen sizes

### Running Tests

#### Android
```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest

# Lint checks
./gradlew lint
```

#### iOS
```bash
# In Xcode: Product → Test (⌘+U)
# Or command line:
xcodebuild test -scheme AppDimens -destination 'platform=iOS Simulator,name=iPhone 14'
```

### Test Coverage

- **Minimum Coverage**: 80% for new code
- **Critical Paths**: 100% coverage for critical functionality
- **Edge Cases**: Test edge cases and error conditions

## 🚀 Release Process

### Version Numbering

We follow [Semantic Versioning](https://semver.org/):

- **MAJOR**: Breaking changes
- **MINOR**: New features (backward compatible)
- **PATCH**: Bug fixes (backward compatible)

### Release Checklist

1. **Update Version**: Update version numbers in all relevant files
2. **Update Changelog**: Update CHANGELOG.md with new features and fixes
3. **Run Tests**: Ensure all tests pass
4. **Update Documentation**: Update documentation for new features
5. **Create Release**: Create release on GitHub
6. **Publish**: Publish to relevant package managers

### Release Files

- **Android**: AAR files, Maven coordinates
- **iOS**: Framework, CocoaPods, Swift Package Manager
- **Documentation**: Updated documentation and examples

## 🏷 Labels and Milestones

### Issue Labels

- `bug`: Something isn't working
- `enhancement`: New feature or request
- `documentation`: Improvements or additions to documentation
- `good first issue`: Good for newcomers
- `help wanted`: Extra attention is needed
- `question`: Further information is requested
- `wontfix`: This will not be worked on

### Pull Request Labels

- `ready for review`: Ready for maintainer review
- `needs testing`: Requires additional testing
- `breaking change`: Breaking change that requires major version bump
- `performance`: Performance improvement

## 📞 Getting Help

### Communication Channels

- **GitHub Issues**: For bug reports and feature requests
- **GitHub Discussions**: For questions and general discussion
- **Email**: for private matters

### Response Times

- **Issues**: Within 48 hours
- **Pull Requests**: Within 72 hours
- **Questions**: Within 24 hours

## 🎉 Recognition

Contributors will be recognized in:

- **README**: Listed as contributors
- **Release Notes**: Mentioned in release notes
- **GitHub**: Listed in the contributors section

## 📄 License

By contributing to AppDimens, you agree that your contributions will be licensed under the Apache License 2.0.

## 🙏 Thank You

Thank you for contributing to AppDimens! Your contributions help make responsive design accessible to developers worldwide.

---

**Happy Contributing!** 🚀