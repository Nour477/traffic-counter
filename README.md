# Traffic Counter

A Java command-line application that reads traffic count data from a file and outputs comprehensive statistics including total cars, daily totals, top 3 half-hour periods, and the least busy 1.5-hour period.

## Requirements

- **Java 21** or higher
- **Maven 3.6+** (for building)

## Building the Application

### Prerequisites

Ensure you have Java 21 and Maven installed:

```bash
java -version  # Should show version 21 or higher
mvn -version   # Should show Maven 3.6 or higher
```

### Build Steps

1. **Clone or navigate to the project directory:**
   ```bash
   cd traffic-counter
   ```

2. **Build the JAR file:**
   ```bash
   mvn clean package
   ```
   
   Or using the Maven wrapper (if available):
   ```bash
   ./mvnw clean package
   ```
   
   On Windows:
   ```powershell
   .\mvnw.cmd clean package
   ```

3. **The JAR file will be created at:**
   ```
   target/traffic-counter-0.0.1-SNAPSHOT.jar
   ```

### Build Options

- **Build without running tests:**
  ```bash
  mvn clean package -DskipTests
  ```

- **Run tests only:**
  ```bash
  mvn test
  ```

## Usage

### Basic Usage

```bash
java -jar target/traffic-counter-0.0.1-SNAPSHOT.jar <input-file>
```

### Example

```bash
java -jar target/traffic-counter-0.0.1-SNAPSHOT.jar traffic-data.txt
```

### Input File Format

The input file should contain one record per line in the following format:

```
<timestamp> <car-count>
```

Where:
- `<timestamp>` is in ISO 8601 format: `yyyy-MM-ddTHH:mm:ss` (e.g., `2021-12-01T05:00:00`)
- `<car-count>` is a non-negative integer
- Records are separated by newlines
- Empty lines are ignored

**Example input file:**
```
2021-12-01T05:00:00 5
2021-12-01T05:30:00 12
2021-12-01T06:00:00 14
2021-12-01T06:30:00 15
2021-12-01T07:00:00 25
2021-12-01T07:30:00 46
2021-12-01T08:00:00 42
2021-12-02T09:00:00 20
2021-12-02T09:30:00 15
```

### Output Format

The application outputs four sections:

1. **Total cars** - A single integer representing the sum of all car counts
2. **Daily totals** - One line per date in format `yyyy-MM-dd <total>` (sorted by date)
3. **Top 3 half-hours** - Three lines showing the half-hour periods with most cars, in format `yyyy-MM-ddTHH:mm:ss <count>` (sorted by count descending)
4. **Least 1.5 hour period** - Three lines showing the 3 contiguous half-hour records with the least total cars, in format `yyyy-MM-ddTHH:mm:ss <count>`

**Example output:**
```
194

2021-12-01 159
2021-12-02 35

2021-12-01T07:30:00 46
2021-12-01T08:00:00 42
2021-12-01T07:00:00 25

2021-12-01T05:00:00 5
2021-12-01T05:30:00 12
2021-12-01T06:00:00 14
```

### Error Handling

- If no file path is provided, the application displays usage information and exits with code 1
- If the file cannot be read or parsed, an error message is displayed and the application exits with code 1
- Empty files result in a message: "No traffic records found in file."

## Project Structure

```
traffic-counter/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/example/trafficcounter/
│   │           ├── TrafficCounterApplication.java    # Main entry point
│   │           ├── TrafficCounterRunner.java         # Orchestrates the flow
│   │           ├── domain/
│   │           │   └── TrafficRecord.java            # Domain model
│   │           ├── parser/
│   │           │   └── TrafficFileParser.java        # File parsing logic
│   │           ├── service/
│   │           │   └── TrafficStatisticsService.java #  formatting
│   └── test/
│       └── java/
│           └── com/example/trafficcounter/
│               ├── parser/
│               ├── service/
│               └── integration/
├── pom.xml                                           # Maven 
└── README.md                                         # This file
```

## Architecture

The application follows a clean layered architecture:

1. **Domain Layer** - `TrafficRecord` represents a single half-hour traffic count
2. **Parser Layer** - Reads and parses input files into domain objects
3. **Service Layer** - Computes all required statistics using efficient algorithms
4. **Runner Layer** - Orchestrates the complete flow: parse → calculate → format → output

## Features

- ✅ Efficient processing using Java streams
- ✅ Well-tested with high code coverage
- ✅ Clean, maintainable code structure
- ✅ No external dependencies (except JUnit for testing)

## Testing

Run all tests:

```bash
mvn test
```

The project includes:
- Unit tests for parser, service, and formatter components
- Integration tests for end-to-end scenarios
- Edge case coverage (empty files, single records, etc.)
