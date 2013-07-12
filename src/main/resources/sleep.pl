use strict;
use warnings;

# Very simple script to sleep for bit, then print a message.

my $n_secs=shift or die "no n_secs";
$n_secs=~/^\d+$/ && $n_secs>0 or die "n_secs not a positive integer: '$n_secs'";
my $msg=shift or die "no msg";

sleep $n_secs;
print "$msg\n";
exit 0;
