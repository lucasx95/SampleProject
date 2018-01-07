/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SampleProjectTestModule } from '../../../test.module';
import { SampleOneToOneDialogComponent } from '../../../../../../main/webapp/app/entities/sample-one-to-one/sample-one-to-one-dialog.component';
import { SampleOneToOneService } from '../../../../../../main/webapp/app/entities/sample-one-to-one/sample-one-to-one.service';
import { SampleOneToOne } from '../../../../../../main/webapp/app/entities/sample-one-to-one/sample-one-to-one.model';

describe('Component Tests', () => {

    describe('SampleOneToOne Management Dialog Component', () => {
        let comp: SampleOneToOneDialogComponent;
        let fixture: ComponentFixture<SampleOneToOneDialogComponent>;
        let service: SampleOneToOneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SampleProjectTestModule],
                declarations: [SampleOneToOneDialogComponent],
                providers: [
                    SampleOneToOneService
                ]
            })
            .overrideTemplate(SampleOneToOneDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleOneToOneDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleOneToOneService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SampleOneToOne(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.sampleOneToOne = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sampleOneToOneListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SampleOneToOne();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.sampleOneToOne = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sampleOneToOneListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
