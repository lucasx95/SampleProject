/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { SampleProjectTestModule } from '../../../test.module';
import { SampleManyDetailComponent } from '../../../../../../main/webapp/app/entities/sample-many/sample-many-detail.component';
import { SampleManyService } from '../../../../../../main/webapp/app/entities/sample-many/sample-many.service';
import { SampleMany } from '../../../../../../main/webapp/app/entities/sample-many/sample-many.model';

describe('Component Tests', () => {

    describe('SampleMany Management Detail Component', () => {
        let comp: SampleManyDetailComponent;
        let fixture: ComponentFixture<SampleManyDetailComponent>;
        let service: SampleManyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SampleProjectTestModule],
                declarations: [SampleManyDetailComponent],
                providers: [
                    SampleManyService
                ]
            })
            .overrideTemplate(SampleManyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleManyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleManyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SampleMany(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sampleMany).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
