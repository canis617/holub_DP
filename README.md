# 변경 사항 정리

## 기본 설정

- [.gitignore](.gitignore) 파일 추가
- [README.md](README.md) 파일 추가
- [JUNIT5.3](foo.imi) 추가를 위해 foo.imi 파일 수정

## HTMLExporter 구현

- [HTMLExporter](src/com/holub/database/HTMLExporter.java)
  - Table.Exporter를 확장하여 구현
- [ConcreteTable](src/com/holub/database/ConcreteTable.java)
   - 테스트 코드 구현
- [테스트](test/com/holub/database/ConcreteTableTest.java)
- [결과물](people.html)

## XMLExporter 및 XMLImporter 구현

- [XMLExporter](src/com/holub/database/XMLExporter.java)
  - Table.Exporter를 확장하여 구현
- [XMLImporter](src/com/holub/database/XMLImporter.java)
  - Table.Importer를 확장하여 구현
- [ConcreteTable](src/com/holub/database/ConcreteTable.java)
   - 테스트 코드 구현
- [테스트](test/com/holub/database/ConcreteTableTest.java)
- [결과물](people.xml)

## STAR JOIN problem 구현

- [Table](src/com/holub/database/Table.java)
  - 인터페이스에 getColumns 추가
- [ConcreteTable](src/com/holub/database/ConcreteTable.java) 클래스
  - getColumns 함수 구현
  - STAR token이 들어왔을 때를 위한 columnNames == null일때의 로직 추가
- [UnmodifiableTable](src/com/holub/database/UnmodifiableTable.java) 클래스
  - getColumns 함수 구현
- [테스트](test/com/holub/database/ConcreteTableTest.java)
- __확인!__
  - Test를 할 때 기존의 Table을 사용하게 되면 people, address의 __addrId__ 가 아닌 __addId__ 임에 유의

## DISTINCT 구현

- [Database](src/com/holub/database/Database.java)
  - DISTINCT token 추가
  - statment()에 DISTINCT를 판단하는 부분 추가
  - doSelect()에 isDistinct 파라미터 추가
  - doSelect()에서 Table의 select()함수를 통해 테이블을 가져온 뒤 DistinctTable로 감싼다.
- [DistinctTable](src/com/holub/database/DistinctTable.java)
  - UnmodifiableTable을 참조하여 구성
  - doDistinct() 메소드를 생성자에서 호출, 구현
  - contains() 메소드를 통해 테이블에서 레코드 존재 여부 확인
- [테스트](test/com/holub/database/DistinctTableTest.java)
