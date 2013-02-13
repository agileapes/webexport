Core Components
===============

Core components of the WebExport project are built on top of a few key concepts, and this document is a short reference
and elaboration of these ideas.

The Workings
------------

The ecosystem of this framework follows a general routine:

  1. Initiating with the start state
  2. Discovering transitions from the given state
  3. Transforming and unifying addresses
  4. Capturing the state
  5. Redirecting to parsers
  6. Storing meta models
  7. Choosing next states and following with step 1 as a fork
  8. Decorating meta models and wrapping up in a separate fork

A detailed explanation of the concepts used in the above listing will follow.

It is useful to note that the asynchronousity suggested in steps 7 and 8 can be overlooked, and the whole process can be
implemented as a synchronous application.

To give a short summary of it, the web is looked at as a **Humungous&trade;** state machine, with every node in it capable
of being a starting state. Therefore it should come as no surprise that many of the ideas discussed here originate or
strongly resemble that of the state-machine model.


Concepts
--------

* **State**

    Each state is a representative of the given situation of the program, as it is in any given moment. In brief, a *state*
    encapsulates how a given web page looks at any given moment.

    So, naturally, a state is a combination of a timestamp of arrival, and a content cache of the given web page at the
    specified address.

* **Transition**

    A *transition*, describes a process that will change the state of the program. So, it is naturally a descriptor for any
    given link in a web page.

* **Meta Model**

    The *meta model*, as the name suggests is an abstract representative of any given state. This model will be contained
    and preserved for later usage by the other components of the framework.

* **Transformers**

    *Transformers* -- no reference to the buckbuster movie there -- are a set of well defined extensions that will transform
    any given address into another address. These will work on the address (a URL) in a synchronous, chain-like manner, so
    that a unified view of the given state space is provided to the application.

* **Redirects**

    *Redirects* are extensions that will tell the application which -- if any -- of the *parsers* should be used for any
    given *transition* at any present *state*.

* **Rules**

    *Rules* are what specify *redirects*.

* **Decorators**

    *Decorators* are agents within the system which will decide what to do with any given meta model.

* **Parsers**

    *Parsers* are standalone extensions that will take the *state* descriptor and generate *meta model*s for them. Individual
    parsers can be written for individual *transition*s.