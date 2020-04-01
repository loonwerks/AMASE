# Safety Annex

The Safety Annex for the Architecture Analysis and Design Language ([AADL](https://aadl.info) 
provides the ability to reason about faults and faulty component behaviors in AADL models. 
In the Safety Annex approach, we use formal assume-guarantee contracts to define the 
nominal behavior of system components. The nominal model is then verified using the 
Assume Guarantee Reasoning Environment ([AGREE](http://loonwerks.com/tools/agree.html) ). 
The Safety Annex provides a way to weave faults into the nominal system model and analyze 
the behavior of the system in the presence of faults. 
The Safety Annex also provides a library of common fault node 
definitions that is customizable to the needs of system and safety engineers. Both AGREE and 
the Safety Annex are integrated into the [OSATE](https://osate.org) AADL model development
environment.

## User Guide

The user's guide for the Safety Annex, including description of the plug-in for
OSATE, fault modeling concepts, specification language reference
manual, and examples is contained in the [Safety Annex Github repository](https://github.com/loonwerks/AMASE/tree/master/doc) .

## Development Guide

See the main [Safety Annex](https://github.com/loonwerks/AMASE.git)
repository for information regarding developing and maintaining the Safety Annex.

