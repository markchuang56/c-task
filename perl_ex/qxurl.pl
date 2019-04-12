#!/usr/bin/perl -n00
# qxurl - tchrist@perl.com
print "$2\n" while m{
        < \s*
  A \s+ HREF \s* = \s* (["']) (.*?) \1
        \s* >
}gsix;