#include <stdio.h>

extern struct rtpkt {
  int sourceid;       /* id of sending router sending this pkt */
  int destid;         /* id of router to which pkt being sent 
                         (must be an immediate neighbor) */
  int mincost[4];    /* min cost to node 0 ... 3 */
};

extern int TRACE;
extern int YES;
extern int NO;

struct distance_table 
{
  int costs[4][4];
} dt0;

//Use this to correctly define the Node ID
int NODEID0 = 0;

/* students to write the following two routines, and maybe some others */

void rtinit0() 
{
  printf("AT: node0.rtinit0() ... \n");

  //Iterate through Distance Table and initialize values to Infinity (999)!
  int i = 0;
  for (i = 0; i < 4; i++){
    int j = 0;
    for (j = 0; j < 4; j++){
      dt0.costs [i][j] = 999;
    }
  }

  //And Initialize the first neighbor values!
  dt0.costs [0][0] = 0;
  dt0.costs [1][1] = 1;
  dt0.costs [2][2] = 3;
  dt0.costs [3][3] = 7;

  //Create the Packet that Node 0 will send to its
  //adjacent neighbors with updated link costs.
  struct rtpkt updatePkt;
  updatePkt.sourceid = 0;

  int index = 0;
  for (index = 0; index < 4; index++)
    updatePkt.mincost [index] = dt0.costs[index][index];

  //To Node 1
  updatePkt.destid = 1;
  tolayer2(updatePkt);

  //To Node 2
  updatePkt.destid = 2;
  tolayer2(updatePkt);

  //To Node 3
  updatePkt.destid = 3;
  tolayer2(updatePkt);

  //DEBUG
  printf("Node 0 just sent off the packet {0,1,3,7} to Node 1, 2, and 3! \n");
  printdt0(&dt0);

}

void rtupdate0(rcvdpkt)
     struct rtpkt *rcvdpkt;
{
  printf("AT: node0.rtupdate0() ... \n");
  int neighborid = rcvdpkt->sourceid;
  int index = 0;
  int linkCostChange = 0; //Set to FALSE
  int * neighborCosts = rcvdpkt->mincost;

  printf("This packet is {%d,%d,%d,%d} \n", neighborCosts[0],neighborCosts[1],neighborCosts[2],neighborCosts[3]);

  //Iterate over the cells in the neighbor's distance vector.
  for (index = 0; index < 4; index++){

    //If the sum of (the cost from this node to the current neighbor) AND (the min cost of the current cell from the neighbor's distance vector)
    //IS LESS THAN
    //The cost of this node going to itself VIA the neighbor...
    if((dt0.costs [neighborid][neighborid] + neighborCosts [index]) < dt0.costs [index][neighborid]){

      //Flip it around: The least cost is now the sum of this node's cost to travel to its neighbor AND the min cost of the current cell from neighbor's distance vector.
      dt0.costs [index][neighborid] = dt0.costs [neighborid][neighborid] + neighborCosts [index];
      linkCostChange = 1; //Keep it off; we now know that we need to update our neighbors!
    }
  }

  //If there was a link cost change, then update neighbors!
  if (linkCostChange == 1){

    printf ("There is a LINK COST CHANGE: Node 0 will send updates to Node 1,2, and 3! \n\n");

    //Create the Packet that Node 0 will send to its
    //adjacent neighbors with updated link costs.
    struct rtpkt updatePkt;
    updatePkt.sourceid = 0;

    //Form a Distance Vector to send out to the neighbors!
    int distanceVector [4];
    int minCost = 999;

    //Iterate over this node's distance table and find the minimum cost for each neighbor.
    int i,j;
    for (i = 0; i < 4; i++){
      for(j = 0; j < 4; j++){

	//Look for the least cost from Node to Destination.
	if (dt0.costs [i][j] < minCost)
	  minCost = dt0.costs [i][j];
      }

      //Supply the distance vector.
      distanceVector [i] = minCost;
      minCost = 999;
    }

    //Set up the packet.
    for (i = 0; i < 4; i++)
      updatePkt.mincost [i] = distanceVector [i];

    //To Node 1
    updatePkt.destid = 1;
    tolayer2(updatePkt);

    //To Node 2
    updatePkt.destid = 2;
    tolayer2(updatePkt);

    //To Node 3
    updatePkt.destid = 3;
    tolayer2(updatePkt);

    //DEBUG
    printf("Node 0 just sent off the packet {%d,%d,%d,%d} to Node 1, 2, and 3! \n", distanceVector[0], distanceVector[1], distanceVector[2], distanceVector[3]);
    printdt0(&dt0);

  }//end if linkCostChange
  printf("\n\n");
}


printdt0(dtptr)
struct distance_table *dtptr;
  
{
  printf("CURRENT TABLE FOR NODE[0]: \n\n");
  printf("                via     \n");
  printf("   D0 |    1     2    3 \n");
  printf("  ----|-----------------\n");
  printf("     1|  %3d   %3d   %3d\n",dtptr->costs[1][1],
	 dtptr->costs[1][2],dtptr->costs[1][3]);
  printf("dest 2|  %3d   %3d   %3d\n",dtptr->costs[2][1],
	 dtptr->costs[2][2],dtptr->costs[2][3]);
  printf("     3|  %3d   %3d   %3d\n",dtptr->costs[3][1],
	 dtptr->costs[3][2],dtptr->costs[3][3]);
  printf("\n");
}

linkhandler0(linkid, newcost)   
int linkid, newcost;

/* called when cost from 0 to linkid changes from current value to newcost*/
/* You can leave this routine empty if you're an undergrad. If you want */
/* to use this routine, you'll need to change the value of the LINKCHANGE */
/* constant definition in prog3.c from 0 to 1 */
	
{
}
