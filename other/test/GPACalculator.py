#! /usr/bin/env python

    #Current Variables
current_GPA = input("Enter Current Cumulative GPA:")
current_Num_Credits = input ("Enter Current Number of Credits Completed (Look on Acad. Requirements on SPIRE):")
current_Num_Vacant_Credits = input ("Of those credits, how many credits are NOT COUNTED towards your current GPA (AP Tests, Internships, Non-graded TAing, etc):")

    #Get the right amount of current credits
current_Num_Credits = current_Num_Credits - current_Num_Vacant_Credits

    #Future Variables
future_Num_Credits = input ("Enter Forecasted Number of Credits (including this semester):"
future_Num_Vacant_Credits = input ("Enter Forecasted Number of Credits that are NOT COUNTED towards your GPA:")

    #Get the right amount of future credits
future_Num_Credits = future_Num_Credits - future_Num_Vacant_Credits

    #Future GPA
future_GPA = input("Enter planned GPA based on Forecasted Number of Credits (Ex: To find the highest GPA possible, enter 4.0:")

    #Some Math
total_Num_Credits = (current_Num_Credits + future_Num_Credits)
projected_GPA = ((current_Num_Credits * current_GPA + future_Num_Credits * future_GPA)/total_Num_Credits)
print "Your Projected GPA after graduating based upon a GPA of", future_GPA_Scale, "and taking", future_Num_Credits, "credits is = ", projected_GPA
