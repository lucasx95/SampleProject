/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SampleProjectTestModule } from '../../../test.module';
import { SampleManyDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/sample-many/sample-many-delete-dialog.component';
import { SampleManyService } from '../../../../../../main/webapp/app/entities/sample-many/sample-many.service';

describe('Component Tests', () => {

    describe('SampleMany Management Delete Component', () => {
        let comp: SampleManyDeleteDialogComponent;
        let fixture: ComponentFixture<SampleManyDeleteDialogComponent>;
        let service: SampleManyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SampleProjectTestModule],
                declarations: [SampleManyDeleteDialogComponent],
                providers: [
                    SampleManyService
                ]
            })
            .overrideTemplate(SampleManyDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleManyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleManyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
