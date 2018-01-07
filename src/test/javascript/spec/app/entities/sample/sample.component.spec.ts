/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { SampleProjectTestModule } from '../../../test.module';
import { SampleComponent } from '../../../../../../main/webapp/app/entities/sample/sample.component';
import { SampleService } from '../../../../../../main/webapp/app/entities/sample/sample.service';
import { Sample } from '../../../../../../main/webapp/app/entities/sample/sample.model';

describe('Component Tests', () => {

    describe('Sample Management Component', () => {
        let comp: SampleComponent;
        let fixture: ComponentFixture<SampleComponent>;
        let service: SampleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SampleProjectTestModule],
                declarations: [SampleComponent],
                providers: [
                    SampleService
                ]
            })
            .overrideTemplate(SampleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Sample(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.samples[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
