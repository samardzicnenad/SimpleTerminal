# SimpleTerminal
##Problem Statement
Consider a store where each item has a price per unit, and may also have a volume price. For example, apples may be $1.00 each or 4 for $3.00. Implement a point-of-sale scanning API that accepts an arbitrary ordering of products (similar to what would happen at a checkout line) and then returns the correct total price for an entire shopping cart based on the per unit prices or the volume prices as applicable.

Here are the products listed by code and the prices to use (there is no sales tax):
<pre>
Product Code | Price
A            | $2.00 each or 4 for $7.00
B            | $12.00
C            | $1.25 or $6 for a six pack
D            | $0.15
</pre>
There should be a top level point of sale terminal service object or namespace that looks something like the pseudo-code below.
<pre>
terminal.setPricing(...)
terminal.scan("A")
terminal.scan("C")... etc.
result = terminal.total
</pre>
Here are the minimal inputs for test cases.
<pre>
Scan these items in this order: ABCDABAA; Verify the total price is $32.40.
Scan these items in this order: CCCCCCC; Verify the total price is $7.25.
Scan these items in this order: ABCD; Verify the total price is $15.40.
</pre>
## Application requirements/limitations:
Pricing file needs to be semicolon separated file with the following structure:
<pre>
item_code;single_price;[volume_qty];[volume_price]
</pre>
##Additional Requirement
Extend your solution by adding an additional pricing option. Item can also have a special price - until a threshold has been reached unit price is applied, but after that all of the products are charged by the special price.
<pre>
Product Code | Price
F            | $1.00 each or $0.90 for 10+ purchase
</pre>
e.g., one item will cost $1.00; 9 items $9.00; but 10 items will also cost $9.00; and 11 will cost $9.90. 
Here are the minimal inputs for test cases.
<pre>
Scan these items in this order: FFFFFFFFF; Verify the total price is $9.00.
Scan these items in this order: FFFFFFFFFF; Verify the total price is $9.00.
Scan these items in this order: FFFFFFFFFFF; Verify the total price is $9.90.
</pre>
## Application requirements/limitations:
Pricing file needs to be semicolon separated file with the following structure:
<pre>
item_code;single_price;[option_type];[option_qty];[option_price]
</pre>
