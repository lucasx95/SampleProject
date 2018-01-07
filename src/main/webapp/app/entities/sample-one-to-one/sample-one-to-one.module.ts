import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SampleProjectSharedModule } from '../../shared';
import {
    SampleOneToOneService,
    SampleOneToOnePopupService,
    SampleOneToOneComponent,
    SampleOneToOneDetailComponent,
    SampleOneToOneDialogComponent,
    SampleOneToOnePopupComponent,
    SampleOneToOneDeletePopupComponent,
    SampleOneToOneDeleteDialogComponent,
    sampleOneToOneRoute,
    sampleOneToOnePopupRoute,
} from './';

const ENTITY_STATES = [
    ...sampleOneToOneRoute,
    ...sampleOneToOnePopupRoute,
];

@NgModule({
    imports: [
        SampleProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SampleOneToOneComponent,
        SampleOneToOneDetailComponent,
        SampleOneToOneDialogComponent,
        SampleOneToOneDeleteDialogComponent,
        SampleOneToOnePopupComponent,
        SampleOneToOneDeletePopupComponent,
    ],
    entryComponents: [
        SampleOneToOneComponent,
        SampleOneToOneDialogComponent,
        SampleOneToOnePopupComponent,
        SampleOneToOneDeleteDialogComponent,
        SampleOneToOneDeletePopupComponent,
    ],
    providers: [
        SampleOneToOneService,
        SampleOneToOnePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SampleProjectSampleOneToOneModule {}
