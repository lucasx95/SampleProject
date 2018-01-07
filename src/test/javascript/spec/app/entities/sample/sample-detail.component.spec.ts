/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { SampleProjectTestModule } from '../../../test.module';
import { SampleDetailComponent } from '../../../../../../main/webapp/app/entities/sample/sample-detail.component';
import { SampleService } from '../../../../../../main/webapp/app/entities/sample/sample.service';
import { Sample } from '../../../../../../main/webapp/app/entities/sample/sample.model';

describe('Component Tests', () => {

    describe('Sample Management Detail Component', () => {
        let comp: SampleDetailComponent;
        let fixture: ComponentFixture<SampleDetailComponent>;
        let service: SampleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SampleProjectTestModule],
                declarations: [SampleDetailComponent],
                providers: [
                    SampleService
                ]
            })
            .overrideTemplate(SampleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Sample(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sample).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
