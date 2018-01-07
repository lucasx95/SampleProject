import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SampleProjectSharedModule } from '../../shared';
import {
    SampleService,
    SamplePopupService,
    SampleComponent,
    SampleDetailComponent,
    SampleDialogComponent,
    SamplePopupComponent,
    SampleDeletePopupComponent,
    SampleDeleteDialogComponent,
    sampleRoute,
    samplePopupRoute,
} from './';

const ENTITY_STATES = [
    ...sampleRoute,
    ...samplePopupRoute,
];

@NgModule({
    imports: [
        SampleProjectSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SampleComponent,
        SampleDetailComponent,
        SampleDialogComponent,
        SampleDeleteDialogComponent,
        SamplePopupComponent,
        SampleDeletePopupComponent,
    ],
    entryComponents: [
        SampleComponent,
        SampleDialogComponent,
        SamplePopupComponent,
        SampleDeleteDialogComponent,
        SampleDeletePopupComponent,
    ],
    providers: [
        SampleService,
        SamplePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SampleProjectSampleModule {}
