# instrument-data

##  Technical specification overview:

You are tasked with producing a standalone component.  This component must conform to these rules.

1. You must use maven to build your application
2. The target after this build must contain a single jar that can be executed without any embedded or otherwise third party lib.  (only core java 8)
3. You can use third party lib’s for testing for example Hamcrest and Mockito are allowed.
4. You can assume that the application will run on a JVM that has infinite virtual memory but has between 4 -12 maximum single CPU core available.

## Epic

Imagine that you work in a small team for an long standing investment bank.
Currently the reference data system in the bank is not fit for purpose so you need to fill the gap.
You are charged with writing an application that can receive instrument data and merge it real time based on a certain business rules.

You are tasked with providing an aggregated view of Instrument reference data. For the purpose of the test we will limit this to the few stories below.

### Story 1:

    Given the “LME” instrument “PB_03_2018” with these details:
    |  LAST_TRADING_DATE             | DELIVERY_DATE |  MARKET                 | LABEL                        |
    |  15-03-2018                    | 17-03-2018    |  PB                     | Lead 13 March 2018           |
    
    When “LME” publishes instrument “PB_03_2018”

    Then the application publishes the following instrument internally
    |  LAST_TRADING_DATE | DELIVERY_DATE  |  MARKET                 | LABEL                             | TRADABLE           |
    |  15-03-2018        | 17-03-2018     |  PB                     | Lead 13 March 2018                |  TRUE              |
    
### Story 2:

    Given the “LME” instrument “PB_03_2018” with these details:
    |  LAST_TRADING_DATE             | DELIVERY_DATE |  MARKET                 | LABEL                  |
    |  15-03-2018                    | 17-03-2018    |  LME_PB                 | Lead 13 March 2018     |
    
    And a  “PRIME” instrument “PRIME_PB_03_2018” with these details:
    |  LAST_TRADING_DATE | DELIVERY_DATE |  MARKET                 | LABEL                  | EXCHANGE_CODE | TRADABLE  |
    |  14-03-2018        | 18-03-2018    |  LME_PB                 | Lead 13 March 2018     | PB_03_2018    | FALSE     |
    
    When “LME” publishes instrument “PB_03_2018”

    Then the application publishes the following instrument internally
    |  LAST_TRADING_DATE | DELIVERY_DATE |  MARKET      | LABEL                 |  TRADABLE     |
    |  15-03-2018        | 17-03-2018    |  LME_PB      | Lead 13 March 2018    |  TRUE         |
    
    When “PRIME” publishes instrument “PB_03_2018”

    Then the application publishes the following instrument internally
    |  LAST_TRADING_DATE             | DELIVERY_DATE |  MARKET                 | LABEL                           |  TRADABLE      |
    |  15-03-2018                    | 17-03-2018    |  PB                     | Lead 13 March 2018              |  FALSE         |
    
The two story above emphasises these rules:

* We trust/use the last trading date and delivery date from the LME exchange over that of PRIME.
* However we enforce the TRADABLE flag from PRIME in all cases.
* Please note in this case the instruments are mapped by LME code and PRIME exchange code.
* However the mapping of instruments might not always be done in this way and the way the mapping key is generate must be potentially flexible at runtime within the code.

The system must be built with the idea that there could be ‘N’ number of sources for a merged instrument and that many weird and wonderful rules can be accommodated easily within the code.

Other vital information:

* There is not a fixed number of instruments to support and this is a variable that will change at runtime.
* You are required to make it easy to add new merge rules into the code.
* You must have comprehensive testing and the solution must be appropriately scalable and thread safe.
* This is meant to be a very simple application DO NOT OVER ENGINEER IT!!.  We require a simple code only NO CONFIGURATION! application.
* There are some obvious patterns expected in the code and we are interested in seeing simple elegant solution.
