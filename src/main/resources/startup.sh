#!/bin/sh
PATH=/sbin:/usr/sbin:/bin:/usr/bin
export PATH
if [ `id -u` -ne 0 ]; then
   echo "You need root privileges to run this script"
   exit 1
fi

JAVA_HOME=/opt/jdk
name=tsied-etl
pidfile="/var/run/$name.pid"

TSIED_USER=root
TSIED_GROUP=root
TSIED_HOME=/data/tsied_elt

TSIED_LOG_DIR=/var/log/tsied
LS_OPEN_FILES=16384
LS_NICE=19

[ -r /etc/default/$name ] && . /etc/default/$name
[ -r /etc/sysconfig/$name ] && . /etc/sysconfig/$name

program="${JAVA_HOME}/bin/java -Xms4096m -Xmx4096m -Dtsied.log.dir=${TSIED_LOG_DIR} -jar ${TSIED_HOME}/tsied_etl.jar "

start() {

  # Run the program!
  $program  > /dev/null &

  # Generate the pidfile from here. If we instead made the forked process
  # generate it there will be a race condition between the pidfile writing
  # and a process possibly asking for status.
  echo $! > $pidfile

  echo "$name started."
  return 0
}

stop() {
  # Try a few times to kill TERM the program
  if status ; then
    pid=`cat "$pidfile"`
    echo "Killing $name (pid $pid) with SIGTERM"
    kill -TERM $pid
    # Wait for it to exit.
    for i in 1 2 3 4 5 ; do
      echo "Waiting $name (pid $pid) to die..."
      status || break
      sleep 1
    done
    if status ; then
      echo "$name stop failed; still running."
    else
      echo "$name stopped."
    fi
  fi
}

status() {
  if [ -f "$pidfile" ] ; then
    pid=`cat "$pidfile"`
    if kill -0 $pid > /dev/null 2> /dev/null ; then
      # process by this pid is running.
      # It may not be our pid, but that's what you get with just pidfiles.
      # TODO(sissel): Check if this process seems to be the same as the one we
      # expect. It'd be nice to use flock here, but flock uses fork, not exec,
      # so it makes it quite awkward to use in this case.
      return 0
    else
      return 2 # program is dead but pid file exists
    fi
  else
    return 3 # program is not running
  fi
}

force_stop() {
  if status ; then
    stop
    status && kill -KILL `cat "$pidfile"`
  fi
}


case "$1" in
  start)
    status
    code=$?
    if [ $code -eq 0 ]; then
      echo "$name is already running"
    else
      start
    fi
    exit $code
    ;;
  stop) stop ;;
  force-stop) force_stop ;;
  status) 
    status
    code=$?
    if [ $code -eq 0 ] ; then
      echo "$name is running"
    else
      echo "$name is not running"
    fi
    exit $code
    ;;
  restart) 
    
    stop && start 
    ;;
  *)
    echo "Usage: $SCRIPTNAME {start|stop|force-stop|status|restart}" >&2
    exit 3
  ;;
esac

exit $?
