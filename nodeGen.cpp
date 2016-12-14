#include <iostream>
#include <ctime>
#include <cstdlib>
#include <fstream>
#include <string>
#include <stdio.h>

using namespace std;

int main()
{
	//ofstream myfile;
	//myfile.open("nodes.txt");
	
	FILE *myfile = fopen("nodes.txt", "w");
	
	srand(time(NULL));
	int n, index = 1;
	
	cout << "Num of Nodes? ";
	cin >> n;
	//string nodes[n];
	//int temp = n;
	
	//for (temp; temp > 0; temp--)
		for(int i = 1; i <= n; i++)
		{
			int nodes = rand() % n + 1;
			float weights = (float)rand() / (float)RAND_MAX;
			//cout << index++ << " " << nodes << " " <<  weights << endl;
			fprintf(myfile, "%d\t%d\t%f\r\n", index++, nodes,weights);
			//myfile << endl;
			
		}
		fclose(myfile);
	//myfile.close();
	
	
	return 0;
}