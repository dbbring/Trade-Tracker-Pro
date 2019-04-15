# Trade-Tracker-Pro
### Trade tracker pro is in beta.
Trade tracker pro is an android app compatible with Lollipop and beyond. T.T.P. Allows you to keep a dedicated trade journal for multiple accounts.
You can also specify multiple categories to file each trade under. T.T.P. allows you to quickly track down which strategies are losing, and which are winning.
## Permissions
T.T.P. does not need any special permissions.


# Changes (4/13/2019):
Tweaked layouts because the user will actually need full width EditText's and so forth.
Created Singleton class of Settings to be accessible throughout program
Implement Important message feature that can be set across the program and save to persistent storage
Integrated settings.txt file for persistent storage of settings.
Integrated charting library (AndroidPlot)
Added Spinner's for the Entry view, and the Settings view which are populated from the settings file

# Working (4/13/2019):
- Adding Journal Entries
- Setting the Settings
- Viewing Journal Entries
- Deleting Journal Entries
- Charts (Somewhat) -> Not accurate Data

# Not Working (4/13/2019):
- 3:05 Reminder
- Saving the Updated Exit Description
- Validation for new entries
- Accurate Charting Data
- Accurate Figures (Percentage change, Money Change, Net Change, etc)
- Exporting Trades to CSV
