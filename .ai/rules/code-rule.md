# Code Rules

## 1. Naming Conventions

### 1.1 View Templates (Mustache)
- **Rule**: All Mustache template files must use **kebab-case** (hyphenated).
- **Example**: 
  - `join-form.mustache` (Correct)
  - `joinForm.mustache` (Incorrect)
  - `join_form.mustache` (Incorrect)

### 1.2 Java Classes
- **Rule**: Follow standard Java naming conventions (PascalCase for classes, camelCase for methods/variables).

### 1.3 Database
- **Rule**: Use snake_case for table and column names (handled by JPA naming strategy).
