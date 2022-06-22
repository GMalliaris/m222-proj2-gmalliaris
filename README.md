## Γιώργος Μάλλιαρης - cs2210017@di.uoa.gr

### Nodes
* b:Block
* t:Transaction
* i:Input
* o:Output

### Relationships
* (b:Block)-[:HAS_TRANSACTION]->(t:Transaction)
* (b:Block)-[:HAS_INPUT]->(i:Input)
* (b:Block)-[:HAS_SPENDING_INPUT]->(i:Input)
* (b:Block)-[:HAS_OUTPUT]->(o:Output)
* (t:Transaction)-[:HAS_INPUT]->(i:Input)
* (t:Transaction)-[:HAS_SPENDING_INPUT]->(i:Input)
* (t:Transaction)-[:HAS_OUTPUT]->(o:Output)

### Init
Κατά το migration των δεδομένων στην βάση δεδομένων χρησιμοποιήθηκε περιορισμένος αριθμίς nodes ώστε να μπορέσει να υλοποιηθέι σε εύλογο χρονικό διάστημα. Συγκεκριμένα, γίνεται processing σε 404 Blocks, 4000 Transactions, 16000 Inputs και 8000 Outpus, ενώ επιπλέον αποθηκεύονται στη βάση μόνο όσα έχουν relationship με Ν Nodes από όσους γίνονται processed.

### Notes
Για την υλοποίηση της εργασίας χρησιμοποιήθηκε `Spring Boot`, ένα `Java` framework λόγω της εξοικείωσης μου με το εν λόγω εργαλείο.
Τα δεδομένα των nodes είναι αυτά ακριβώ που περιγράφονται από την εκφώνηση, με μόνη διαφοροποίηση την αποθήκευση των ημερομηνιών ώς `epochMillis`, έναν ακέραιο αριθμό προκειμένου να διευκολυνθώ στον υπολογισμό των ranges όπου αυτά ζητούνται, ενώ τα `queries` που υλοποιήθηκαν είναι τα 1-5, 8, 11 και 12.

### How To Run
1. Make sure some implementation of jdk11 is installed on your pc
   e.g.  from [here](https://adoptium.net/temurin/releases/ "here") or via package manager
   To verify installation run `java --version` on terminal.
2. `cd m222-proj2-gmalliaris`
3. `./mvnw clean install`
4. `java -jar <-Dspring.profiles.active=init> <-Dspring.neo4j.authentication.username=USERNAME> <-Dspring.neo4j.authentication.password=gmalliaris target/m222-proj2-gmalliaris-0.0.1-SNAPSHOT.jar --inputDir INPUT_DIR`
    + Everything in <> is optional
    + use `-Dspring.profiles.active=init` to migrate data to db. If data exist the program will exit.
    + use `-Dspring.neo4j.authentication.username=USERNAME` in case db username is not the 	default <i>neo4j</i>.
    + use `-Dspring.neo4j.authentication.password=PASSWORD` in case the db password is not `gmalliaris` (most probably).
    + use `-Dspring.neo4j.uri=bolt://HOST:PORT` in case neo4j instance host and port are not the default.
    + use `INPUT_DIR` to location of the tsv files. Inside that directory, the blocks .tsv must be in `INPUT_DIR/blocks/blockFile.tsv` format, the transactions in `INPUT_DIR/transactions/transactionFile.tsv` format, the inputs in `INPUT_DIR/inputs/inputFile.tsv` format and the outputs  in `INPUT_DIR/outputs/outputFile.tsv` format. The *.tsv file name is irelevant.

### Queries

1 -> `curl --location --request GET 'http://localhost:8080/transactions/{{TRANSACTION_HASH}}/input-recipients'`
<br/>
2 -> `curl --location --request GET 'http://localhost:8080/transactions/{{TRANSACTION_HASH}}/output-recipients' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "rangeStart": "2015-12-01 00:00:00",
   "rangeEnd": "2023-12-01 00:00:00"
   }'`, dates in `yyyy-MM-dd hh:mm:ss` format
<br/>
3 ->`curl --location --request GET 'http://localhost:8080/{{BLOCK_ID}}/739052/transactions'`
<br/>
4 -> `curl --location --request GET 'http://localhost:8080/outputs/most-transactions' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "date": "2022-06-03"
   }'`, date in `yyyy-MM-dd` format
<br/>
5 -> `curl --location --request GET 'http://localhost:8080/inputs/15FgzJbmoA9p3VoTXiPU65cJvRHEEj6U38' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "date": "2022-06-03"
   }'`, date in `yyyy-MM-dd` format
<br/>
8 -> `curl --location --request GET'http://localhost:8080/blocks/top-miner-reward'` 
<br/>
11 -> `curl --location --request GET 'http://localhost:8080/transactions/largest' \
--header 'Content-Type: application/json' \
--data-raw '{
"rangeStart": "2015-12-01 00:00:00",
"rangeEnd": "2023-12-01 00:00:00"
}'`, dates in `yyyy-MM-dd hh:mm:ss` format
<br/>
12 -> `curl --location --request GET 'localhost:8080/blocks/{{BLOCK_ID}}/top-miner-exchange'`