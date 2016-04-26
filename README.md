# NetworkManagedUI
Android library which makes it easier to disable and enable UI elements whose action require presence of network connection

# Description
As the project gets bigger and needs to work offline/online I have felt need a way to segregrate and control some UI elements to be controlled centrally and can be easily disabled and enabled based on current connectivity state rather than doing network checks each time on handling click for these. So implemented a simple library, which makes it a little simpler to manage this without writing too much code or making each activity network aware. So, now this task requires a few simple steps

1) While making the xml layout, for all the views which require network the actionable add a android:tag with value 'networkdependednt'
2) Once your view is created call NetworkStatusReciever.RegisterParentView() with the parent view element and the context and that's all, your UI elements marked network dependent will be disabled in case network goes
3) In case if you need to apply grayscale to all disabled views you can pass a colorfilter to apply in case of disabling
4) You can also implement a callback to handle network callbacks in case you need specific items
For more details check wiki at https://github.com/sinha-abhishek/NetworkManagedUI/wiki 
    
