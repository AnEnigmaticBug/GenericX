This app is intended to be used as the base upon which the official apps for Oasis18 and APOGEE19 are to be built. Emphasis on the word 'intended'. It may or may not be used. Most probably the latter. ;)

# Road-Map:

To progress with a clear path, here's the intended road-map for this app:
- [X] Make the Event class.
- [X] Decide the interface a Repository should have.
- [X] Setup the Room Library.
- [X] Using the Room Library, make an implementation of Repository. This should be purely Room based i.e no network stuff.
- [X] Setup Dagger2.
- [X] Setup the ViewModel for EventListActivity.
- [X] Make the layouts for EventListActivity.
- [X] Setup a basic EventListActivity(it should be able to display unfiltered data; don't setup bottom nav bar, nav drawer, filtering or favoriting).
- [X] Introduce favoriting. Introduce notifications later. For now, favorites should be able to get stored and retrieved.
- [ ] Make the layouts for Filtering Dialog.
- [ ] Introduce filtering.
- [ ] Setup Firebase.
- [ ] Make another implementation of Repository. This should work using both the Room Library and Firebase.
- [ ] Setup notifications.
- [ ] Get the navigation drawer working. Break this into separate checkpoints.
- [ ] Ready the bottom navigation bar. Again, this can be many checkpoints.
- [ ] Extract out strings from layouts. Add content descriptions.
- [ ] Find a nice icon to go with this app.

This will be updated(can't say regularly... but pretty often) to reflect progress. And obviously, while these checkpoints are meant to be attained in order, there is no restriction on refactoring. Refactor as much as possible.


# Long term goals:

These goals may or may not be implemented. These are 'nice to haves'. They're not central to the app:
- [ ] Setup unit tests.
- [ ] Profile and make the app more efficient.
- [ ] Optimize its size.
